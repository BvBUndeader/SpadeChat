import axios from 'axios'

const userApiBaseUrl = 'http://localhost:6980/users'

class UserService{
    async login(username){
        try{
            const response = await axios.get(`${userApiBaseUrl}/username/${username}`);
            return response.data;
            
        }
        catch(error){
            if (error.response.status === 404) {
                throw new Error("Incorrect username");
              }
              throw new Error("something broke");
            
        }
    }

    async register(username){
        try{
            await axios.post(userApiBaseUrl, { username });
            return true;
        }
        catch(error){
            throw new Error('User with the same username already exists');
        }
    }
}


export default new UserService();