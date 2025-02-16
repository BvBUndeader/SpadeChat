<template>
    <h1 class="h1">Spade Chat</h1>

    <div class="container">
        <h2>Log In</h2>

        <input v-model="loginUsername" placeholder="Enter username" />
        <br>
        <button @click="loginUser">Login</button>

        <br>
        <h2>First time? Register</h2>

        <input v-model="registerUsername" placeholder="Enter username">
        <br>
        <button @click="registerUser">Register</button>

        <br>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>

</template>

<script lang="ts">
import {ref} from 'vue';
import { useRouter } from 'vue-router';
import UserService from '@/services/UserService';

export default{
    setup(){
    const loginUsername = ref('');
    const registerUsername = ref('');
    const errorMessage = ref('');
    const router = useRouter();

    const loginUser = async () => {
        errorMessage.value = '';
        try{
            await UserService.login(loginUsername.value);
            router.push(`/spadechat/${loginUsername.value}`);
        }
        catch(error){
            errorMessage.value = error.message;
        }
    };


    const registerUser = async () => {
        if(!registerUsername.value.trim()){
            errorMessage.value = 'Username field cannot be empty';
            return;
        }
        
        try{
            await UserService.register(registerUsername.value);
            router.push(`/spadechat/${registerUsername.value}`);
        }
        catch (error){
            errorMessage.value = error.message;
        }
    };

    return {loginUsername, registerUsername, loginUser, registerUser, errorMessage};
}
}


</script>

<style scoped>
.container {
  max-width: 400px;
  margin: 0 auto;
  text-align: center;
}
input {
  width: 100%;
  padding: 8px;
  margin: 10px 0;
}
button {
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
.error {
  color: red;
  font-size: 20px;
  font-weight: bold;
}
.h1{
    font-size: 60px;
    font-weight: bold;
    text-align: center;
}
</style>