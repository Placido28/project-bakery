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
                                <div class="mb-3">
                                    <p v-if="errorMessage">{{ errorMessage }}</p>
                                </div>
                                <form class="needs-validation">
                                    <div class="row g-3">
                                        <!-- row -->
                                        <div class="col-12">
                                            <!-- input -->
                                            <label for="formSigninEmail" class="form-label visually-hidden">Email
                                                address</label>
                                            <input v-model="email" type="email" class="form-control" id="formSigninEmail"
                                                placeholder="Email" required>
                                            <div class="invalid-feedback">Please enter name.</div>
                                        </div>
                                        <div class="col-12">
                                            <!-- input -->
                                            <div class="password-field position-relative">
                                                <label for="formSigninPassword"
                                                    class="form-label visually-hidden">Password</label>
                                                <div class="password-field position-relative">
                                                    <input v-model="password" type="password" class="form-control fakePassword"
                                                        id="formSigninPassword" placeholder="*****" required>
                                                    <span><i class="bi bi-eye-slash passwordToggler"></i></span>
                                                    <div class="invalid-feedback">Please enter password.</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-md-flex justify-content-between">
                                            <!-- form check -->
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value=""
                                                    id="flexCheckDefault">
                                                <!-- label -->
                                                <label class="form-check-label" for="flexCheckDefault">Remember
                                                    me</label>
                                            </div>
                                            <div>
                                                <a href="./forgot-password.html" class="text-link">Forgot password?</a>
                                            </div>
                                        </div>
                                        <!-- btn -->
                                        <div class="col-12 d-grid"><button type="submit" class="btn btn-primary">Sign
                                                In</button></div>
                                        <!-- link -->
                                    </div>
                                    <div class="ho">
                                        <div class="l">
                                            <div aria-hidden="true" class="j u la aaz">
                                                <div class="vo ahn aic"></div>
                                            </div>
                                            <div class="l la abf aze azp"><span class="aoc auc baw">Or continue
                                                    with</span>
                                            </div>
                                        </div>
                                        <div class="hk lc aai abn"><a href="#"
                                                class="la vo aaz abf abm agd aoc aty aul ayp azr baw ber bff bfq bjg bsy cam"><svg
                                                    viewBox="0 0 24 24" aria-hidden="true" class="pp ts">
                                                    <path
                                                        d="M12.0003 4.75C13.7703 4.75 15.3553 5.36002 16.6053 6.54998L20.0303 3.125C17.9502 1.19 15.2353 0 12.0003 0C7.31028 0 3.25527 2.69 1.28027 6.60998L5.27028 9.70498C6.21525 6.86002 8.87028 4.75 12.0003 4.75Z"
                                                        fill="#EA4335"></path>
                                                    <path
                                                        d="M23.49 12.275C23.49 11.49 23.415 10.73 23.3 10H12V14.51H18.47C18.18 15.99 17.34 17.25 16.08 18.1L19.945 21.1C22.2 19.01 23.49 15.92 23.49 12.275Z"
                                                        fill="#4285F4"></path>
                                                    <path
                                                        d="M5.26498 14.2949C5.02498 13.5699 4.88501 12.7999 4.88501 11.9999C4.88501 11.1999 5.01998 10.4299 5.26498 9.7049L1.275 6.60986C0.46 8.22986 0 10.0599 0 11.9999C0 13.9399 0.46 15.7699 1.28 17.3899L5.26498 14.2949Z"
                                                        fill="#FBBC05"></path>
                                                    <path
                                                        d="M12.0004 24.0001C15.2404 24.0001 17.9654 22.935 19.9454 21.095L16.0804 18.095C15.0054 18.82 13.6204 19.245 12.0004 19.245C8.8704 19.245 6.21537 17.135 5.2654 14.29L1.27539 17.385C3.25539 21.31 7.3104 24.0001 12.0004 24.0001Z"
                                                        fill="#34A853"></path>
                                                </svg><span class="aze azr">Google</span></a><a href="#"
                                                class="la vo aaz abf abm agd aoc aty aul ayp azr baw ber bff bfq bjg bsy cam"><svg
                                                    xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                    fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                                                    <path
                                                        d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
                                                </svg><span class="aze azr">Facebook</span></a></div>
                                    </div>
                                </form>
                                <div class="ho ayb aze bas">
                                    Don’t have an account?
                                    <a href="./signup" class="text-link">Sign Up</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { login, saveToken } from '@/service/authService';

export default {
  setup() {
    const email = ref('');
    const password = ref('');
    const errorMessage = ref('');

    const handleLogin = async () => {
      try {
        const response = await login({ email: email.value, password: password.value });

        if (response.accessToken) {
          saveToken(response.accessToken, response.refreshToken);
          window.location.href = '/dashboard'; // Redirige tras el login exitoso
        } else {
          errorMessage.value = 'Error en la autenticación';
        }
      } catch (error) {
        errorMessage.value = 'Credenciales incorrectas';
      }
    };

    return { email, password, errorMessage, handleLogin };
  }
};
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
</style>