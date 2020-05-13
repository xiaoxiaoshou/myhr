import axios from 'axios'
import {Message} from "element-ui";
import router from '../router'

/**
 * 响应拦截器(错误统一处理)
 */
axios.interceptors.response.use(success =>{
  /**
   * success中的status(http中的)与data中的status(后台返回的)不是同一个
   */
  console.log(success.data.status)
  if(success.status && success.status == 200 && success.data.status == 500){
    Message.error({message:success.data.msg})
  }
  if(success.data.msg){
    Message.success({message:success.data.msg})
  }
  return success.data
},error => {

  if (error.response.status == 504 || error.response.status == 404) {
    Message.error({message: '服务器被吃了( ╯□╰ )'})
  } else if (error.response.status == 403) {
    Message.error({message: '权限不足，请联系管理员'})
  } else if (error.response.status == 401) {
    Message.error({message: '尚未登录，请登录'})
     router.replace('/');
  }else{
    if(error.response.data.msg){
      Message.error({message: error.response.data.msg})
    }else{
      Message.error({message: '未知错误！'})
    }
  }

})

let base=''

/**
 * 登录请求
 * @param url
 * @param params
 * @returns {AxiosPromise}
 */
export const postKeyValueRequest = (url,params) => {
  return axios({
    method:'post',
    url:`${base}${url}`,
    data:params,
    transformRequest:[function (data) {
      //修改响应数据
      let ret = ''
        for(let i in data){
          ret += encodeURIComponent(i)+ '='+ encodeURIComponent(data[i])+'&'
        }
       return ret
    }],
    headers:{
      //自定义请求头
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params: params
  })
}
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    params: params
  })
}
