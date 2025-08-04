<template>
  <div class="register-container">
    <div class="register-card">
      <h2>Register</h2>

      <Form @submit="onSubmit" :validation-schema="registerSchema">
        <div class="form-group">
          <label for="username">Username</label>
          <Field name="username" placeholder="Enter your username" />
          <ErrorMessage name="username" class="error" />
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <Field name="email" type="email" placeholder="Enter your email" />
          <ErrorMessage name="email" class="error" />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <Field name="password" type="password" placeholder="Enter your password" />
          <ErrorMessage name="password" class="error" />
        </div>

        <div class="form-group">
          <label for="confirmPassword">Confirm Password</label>
          <Field name="confirmPassword" type="password" placeholder="Confirm password" />
          <ErrorMessage name="confirmPassword" class="error" />
        </div>

        <p v-if="error" class="error">{{ error }}</p>

        <button type="submit">Register</button>

        <p class="login-link">
          Have an account? <router-link to="/login">Login</router-link>
        </p>
      </Form>
    </div>
  </div>
</template>

<script setup>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from '../axios';

const router = useRouter();
const error = ref('');

const registerSchema = yup.object({
  username: yup.string().required('Username is required'),
  email: yup.string().email('Invalid email').required('Email is required'),
  password: yup.string().min(6, 'Password must be at least 6 characters').required(),
  confirmPassword: yup
    .string()
    .required('Please confirm your password')
    .oneOf([yup.ref('password')], 'Passwords do not match'),
});

const onSubmit = async (values) => {
  try {
    
    await axios.post('/auth/register', {
      userName: values.username,
      emailId: values.email,
      password: values.password,
    });
    router.push('/login');
  } catch (err) {
    console.log(err);
    error.value = err.response?.data?.error || 'Registration failed.';
  }
};
</script>




<style scoped>

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background-color: #f3f4f6; 
  padding: 20px;
  box-sizing: border-box;
}

.register-card {
  background: white;
  padding: 30px 40px;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

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
input[type="email"],
input[type="password"],
input,
.field,
.Field {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}

.password-wrapper {
  position: relative;
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

.login-link {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
  color: black;
}

.error {
  color: red;
  margin-top: 1em;
}

</style>