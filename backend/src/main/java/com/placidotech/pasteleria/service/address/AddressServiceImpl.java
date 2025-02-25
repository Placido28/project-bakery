package com.placidotech.pasteleria.service.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.AddressDTO;
import com.placidotech.pasteleria.enums.AddressType;
import com.placidotech.pasteleria.mapper.AddressMapper;
import com.placidotech.pasteleria.model.Address;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.AddressRepository;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.address.CreateAddressRequest;
import com.placidotech.pasteleria.request.address.UpdateAddressRequest;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author PLACIDO
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService{

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    
    @Override
    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return addressMapper.toDTO(address);
    }

    @Override
    public AddressDTO createAddress(CreateAddressRequest request) {
        
        // Buscar al usuario en la base de datos
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Verificar si el usuario ya tiene direcciones registradas
        boolean isFirstAddress = addressRepository.countByUserId(user.getId()) == 0;

        // Convertir el request en AddressDTO
        AddressDTO addressDTO = addressMapper.toDTO(request);
        addressDTO.setUserId(user.getId()); //Asignar el usuario

        // Si es la primera dirección, marcarla como default
        addressDTO.setDefaultAddress(isFirstAddress);

        // Llamar a la lógica para guardar la dirección
        return createAddressLogic(addressDTO);
        
    }
        
    private AddressDTO createAddressLogic(AddressDTO addressDTO) {
        // Convertir el DTO a entidad
        Address address = addressMapper.toEntity(addressDTO);

        //Si la dirección es predeterminada, desmarcar las demás direcciones del usuario
        if (addressDTO.isDefaultAddress()) {
            List<Address> userAddresses = addressRepository.findByUserId(addressDTO.getUserId());
            userAddresses.forEach(existingAddress -> existingAddress.setDefaultAddress(false));
            addressRepository.saveAll(userAddresses); // Guardar las direcciones actualizadas
        }

        // Guardar la dirección en la base de datos
        Address savedAddress = addressRepository.save(address);

        // Convertir la entidad guardada a DTO y devolverla
        return addressMapper.toDTO(savedAddress);
    }
        
    @Override
    public AddressDTO updateAddress(Long id, UpdateAddressRequest request) {
        
        AddressDTO addressDTO = addressMapper.toDTO(request);
        return updateAddressLogic(id, addressDTO);
    }
        
    private AddressDTO updateAddressLogic(Long id, AddressDTO addressDTO) {
        // Buscar la dirección existente
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        // Mantener el estado actual del campo 'defaultAddress' sin alterarlo
        boolean currentDefaultStatus = existingAddress.isDefaultAddress();
        

        //Actualizar los campos de la dirección existente
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setNumbers(addressDTO.getNumbers());
        existingAddress.setLot(addressDTO.getLot());
        existingAddress.setBlock(addressDTO.getBlock());
        existingAddress.setReferencesDetails(addressDTO.getReferences());
        existingAddress.setAddressType(AddressType.valueOf(addressDTO.getAddressType()));

        //Restaurar el estado original de 'defaultAddress'
        existingAddress.setDefaultAddress(currentDefaultStatus);

        // Actualizar el User si es necesario
        if (addressDTO.getUserId() != null) {
            User user = userRepository.findById(addressDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingAddress.setUser(user);
        }

        // Guardar la dirección actualizada
        Address updatedAddress = addressRepository.save(existingAddress);

        return addressMapper.toDTO(updatedAddress);
    }
        
    @Override
    public void deleteAddress(Long id) {
        // Buscar la dirección a eliminar
        Address addressToDelete = addressRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Address not found"));

        Long userId = addressToDelete.getUser().getId(); // Obtener el ID del usuario
        boolean wasDefault = addressToDelete.isDefaultAddress(); // Verificar si era la predeterminada

        // Eliminar la dirección
        addressRepository.delete(addressToDelete);

        // Si la dirección eliminada era la predeterminada, asignar otra dirección como default
        if (wasDefault) {
        List<Address> userAddresses = addressRepository.findByUserId(userId);

            if (!userAddresses.isEmpty()) {
                Address newDefaultAddress = userAddresses.get(0); // Tomar la primera disponible
                newDefaultAddress.setDefaultAddress(true);
                addressRepository.save(newDefaultAddress); // Guardar el cambio
            }
        }
    }

    @Override
    public List<AddressDTO> getAddressesByUserId(Long userId) {
        // Obtener todas las direcciones asociadas a un usuario
        List<Address> addresses = addressRepository.findByUserId(userId);

        return addresses.stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void setDefaultAddress(Long addressId, Long userId){
        // Buscar la dirección seleccionada
        Address newDefaultAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        // Verificar que la dirección pertenece actual y desmarcarla
        if (!newDefaultAddress.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to the user");   
        }

        // Buscar la dirección predeterminada actual y desmarcarla
        addressRepository.findByUserIdAndDefaultAddressTrue(userId)
                .ifPresent(currentDefault -> {
                    currentDefault.setDefaultAddress(false);
                    addressRepository.save(currentDefault);
                });
        
        // Marcar la nueva dirección como predeterminada
        newDefaultAddress.setDefaultAddress(true);
        addressRepository.save(newDefaultAddress);
    }

}
