<template>
    <div class="vh-100 fondo-login">
        <div class="h-100 d-flex flex-column justify-content-center">
            <div class="container">
                <div class="row">
                    <div class="col-xl-5 col-lg-7">
                        <div class="card mx-4 border-0">
                            <div class="card-body p-md-6 p-4">
                                <div class="mb-5 text-center">
                                    <a href="./HomeView.vue">
                                        <img src="../assets/img/logo.png" class="imagen-reducida" alt="logo">
                                    </a>
                                </div>
                                <!-- <div class="mb-3">
                                    <h2 class="fs-4 mb-0">Sign in to your account</h2>
                                </div> -->
                                <form class="needs-validation">
                                    <div class="row g-3">
										<!-- col -->
										<div class="col-md-6 col-12">
											<!-- input -->
											<label for="formSignupfname" class="form-label visually-hidden">First
												Name</label>
											<input type="text" class="form-control" id="formSignupfname" placeholder="First Name" required="">
											<div class="invalid-feedback">Please enter first name.</div>
										</div>
										<div class="col-md-6 col-12">
											<!-- input -->
											<label for="formSignuplname" class="form-label visually-hidden">Last
												Name</label>
											<input type="text" class="form-control" id="formSignuplname" placeholder="Last Name" required="">
											<div class="invalid-feedback">Please enter last name.</div>
										</div>
										<div class="col-12">
											<!-- input -->
											<label for="formSignupEmail" class="form-label visually-hidden">Email
												address</label>
											<input type="email" class="form-control" id="formSignupEmail" placeholder="Email" required="">
											<div class="invalid-feedback">Please enter email.</div>
										</div>
										<div class="col-12">
											<div class="password-field position-relative">
												<label for="formSignupPassword" class="form-label visually-hidden">Password</label>
												<div class="password-field position-relative">
													<input type="password" class="form-control fakePassword" id="formSignupPassword" placeholder="*****" required="">
													<span><i class="bi bi-eye-slash passwordToggler"></i></span>
													<div class="invalid-feedback">Please enter password.</div>
												</div>
											</div>
										</div>
										<!-- btn -->
										<div class="col-12 d-grid"><button type="submit" class="btn btn-primary">Register</button></div>

										<div class="col-12">
											<!-- text -->
											<p class="mb-2">
												I already have an account
												<a href="./signin.html" class="text-link">Sign In</a>
											</p>
											<p class="mb-0">
												<small>
													By continuing, you agree to our
													<a href="#!" class="text-link">Terms of Service</a>
													&amp;
													<a href="#!" class="text-link">Privacy Policy</a>
												</small>
											</p>
										</div>
									</div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>

</script>

<style scoped>
.text-center {
    deight: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0;
    /* Sin padding adicional */
}

.imagen-reducida {
    max-width: 250px;
    /* Ajusta el tamaño según lo necesario */
    margin-bottom: 0px;
    /* Espacio entre la imagen y el texto */
}

button {
    background-color: #d2691e;
    /* Marrón chocolate */
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #a0522d;
    /* Marrón más oscuro */
}

a {
    font-size: 0.9em;
    color: #d2691e;
    text-decoration: none;
    margin-top: 5px;
}

a:hover {
    color: #a0522d;
}

h2 {
    margin-top: 10px;
    /* Ajusta según lo necesario */
}

.ho {
    margin-top: calc(var(--spacing)* 10);
}

.l {
    position: relative;
}
</style>