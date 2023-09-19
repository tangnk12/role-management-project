import axios from 'axios'
import ElementUI from 'element-ui';
const request = axios.create({
    baseURL:'http://localhost:9090',//attention, this added globally with word api, hence on page noneed add api already, if added will be api/api
    timeout:30000
})
//request block interface
//send some message for user to made something
//add global a token
request.interceptors.request.use(config=>{
//    config.headers['Content-Type']='application/json;charset=utf-8';
    let user=localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
    if(user){
        config.headers['token']=user.token;

    }

    return config
},  error=>{
    return Promise.reject(error)
});
//request block interface
//handler result after interface eject
request.interceptors.response.use(
    response=>{
        let res = response.data;
        if(response.config.responseType === 'blob'){
            return res
        }
        if(typeof res === 'string'){
            res=res ? JSON.parse(res):res
        }
        if(res.code==='401'){
            ElementUI.Message({
                message:res.msg,
                type:'error'
            });
        }
            return res;
    },
    error=>{
        console.log('e'+error)//for debug
        return Promise.reject(error)
    }

)
export default request