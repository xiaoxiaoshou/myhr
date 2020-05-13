<template>
  <div>
    <el-form ref="loginForm" :model="loginForm" :rules="rules" class="loginContainer">
      <h3>系统登录</h3>
      <el-form-item  prop="username">
        <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item  prop="password">
        <el-input size="normal" type="password" v-model="loginForm.password" @keydown.enter.native="submitLogin" auto-complete="off" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                  placeholder="点击图片更换验证码" @keydown.enter.native="submitLogin" style="width: 250px"></el-input>
        <img :src="vcUrl" @click="updateVerifyCode" alt="" style="cursor: pointer">
      </el-form-item>
      <el-checkbox  size="normal" class="loginRemember" v-model="checked"></el-checkbox>
      <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: "Login",
    data(){
      return{
        vcUrl: '/verifyCode?time='+new Date(),
        loginForm:{
          username: 'admin',
          password: '123',
          code:''
        },
        checked:true,
        rules:{
          username:[
            {required:true,message:"请输入用户名",trigger:'blur'}
          ],
          password:[
            {required:true,message:"请输入密码",trigger:'blur'}
          ]
        }
      }
    },
    methods:{
      updateVerifyCode(){
        this.vcUrl = '/verifyCode?time='+new Date();
      },
      submitLogin() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            //alert('submit!');
            this.postKeyValueRequest('/doLogin',this.loginForm).then(resp =>{
              if (resp){
                window.sessionStorage.setItem('user',JSON.stringify(resp.obj))
                let path = this.$route.query.redirect;
                console.log(path)
                this.$router.replace((path=='/'||path==undefined)?"/home":path)
              } else{
                this.vcUrl = '/verifyCode?time='+new Date();
              }
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style>
  .loginContainer{
    width: 350px;
    background-clip: padding-box;
    margin: 300px auto;
    padding: 15px 35px 15px 35px;
    box-shadow: 0 0 25px #cac6c6;
    border-radius: 15px;
    border: 1px solid #eaeaea;
    background: #fff;
  }

  .loginTitle {
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
  }

  .loginRemember {
    display: block;
    text-align: left;
    margin: 0px 0px 15px 0px;
  }

</style>
