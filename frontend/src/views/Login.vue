<template>
  <div class="login-container">
    <div class="login-card">
      <h2>Login</h2>

      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            type="text"
            id="username"
            v-model="username"
            required
            placeholder="Enter your username"
          />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <div class="password-wrapper">
            <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password"
              required
              placeholder="Enter your password"
            />
            <span class="eye-icon" @click="togglePassword">
              <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
            </span>
          </div>
        </div>

        <div class="form-links">
          <router-link to="/forgot-password">Forgot Password?</router-link>
        </div>

        <button type="submit">Login</button>

        <p class="register-link">
          Don't have an account?
          <router-link to="/register">Register</router-link>
        </p>

        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </div>
  </div>
</template>

<script>
import axios from '../axios';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

export default {
  setup() {
    const username = ref('');
    const password = ref('');
    const showPassword = ref(false);
    const error = ref('');
    const router = useRouter();

    const togglePassword = () => {
      showPassword.value = !showPassword.value;
    };

    const handleLogin = async () => {
      const User= {
        userName: username.value,
      password: password.value};
      console.log(User);
      try {
        const response = await axios.post('auth/login', {
          userName: username.value,
          password: password.value
        });
        
        const token = response.data['Token: '];
        localStorage.setItem('token', token);

        router.push('/dashboard');
      } catch (err) {
        error.value = err.response.data['Error: '] || 'Login failed';
      }
    };

    return {
      username,
      password,
      showPassword,
      togglePassword,
      error,
      handleLogin,
    };
  },
};
</script>

<!--<style scoped>

</style>
-->
<style scoped>

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background-color: #f3f4f6; 
  padding: 20px;
  box-sizing: border-box;
}


.login-card {
  background: white;
  padding: 30px 40px;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.error {
  color: red;
  margin-top: 1em;
}

/* html, body {
  background-color: #f5f5f5; 
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;  
  overflow-x: hidden; 
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100%;
  background-color: #f3f4f6; 
  padding: 20px;
  box-sizing: border-box;
  overflow-x: hidden; 
}

.login-card {
  background: white;
  padding: 30px 40px;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px; 
  box-sizing: border-box;
} */


h2 {
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}

.password-wrapper {
  position: relative;
}

.eye-icon {
  position: absolute;
  top: 50%;
  right: 12px;
  transform: translateY(-50%);
  cursor: pointer;
  color: #666;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #0056b3;
}

.form-links {
  text-align: right;
  margin-bottom: 10px;
}

.form-links a {
  color: #007bff;
  text-decoration: none;
  font-size: 13px;
}

.form-links a:hover {
  text-decoration: underline;
}

.register-link {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
  color: black;
}
</style>

<!-- Font Awesome (for eye icon) -->
<!-- Add in your index.html or main.js if not already -->
