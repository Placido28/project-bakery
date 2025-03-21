import { Client } from "@stomp/stompjs";
import { logout } from "./authService";

const socketClient = new Client({
    brokerURL: "ws://localhost:8081/ws",
    reconnectDelay: 5000,
});

socketClient.onConnect = () => {
    console.log("Connected to WebSocket");

    socketClient.subscribe("/topic/logout", () => {
        console.log("🚨 Logout automático recibido desde el backend");
        logout(); // 🔥 Cierra la sesión automáticamente
    });
};

socketClient.onStompError = (frame) => {
    console.error("STOMP error:", frame);
};

socketClient.activate();

export default socketClient;