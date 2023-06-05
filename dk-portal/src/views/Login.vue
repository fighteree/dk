<template>
    <div>
        <el-row id="login-page">
            <div class="panel">
                <el-col id="login-box" :span="7">
                <div id="login-title">
                    登录到DK云盘
                </div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="0px"
                    class="demo-ruleForm">

                    <el-form-item prop="email">
                        <el-input type="text" placeholder="请输入邮箱" v-model="ruleForm.email" autocomplete="off">
                        <i style="font-size: 20px;" slot="prefix" class="iconfont icon-youxiang"></i>
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="passWord">
                        <el-input type="password" v-model="ruleForm.passWord" placeholder="请输入密码"
                            autocomplete="off">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="checkCode" id="check-code-item">
                        <el-input v-model="ruleForm.checkCode" placeholder="验证码">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                        <img src="/api/check-code" @click="changeCode" ref="changeCode" style="width: 120px;height: 50px;"
                            alt="">
                        <a href="#" @click="changeCode" style="text-decoration: none;margin-left: 10px;">换一张?</a>
                    </el-form-item>
                </el-form>

                <a href="#/register" style="text-decoration: none; margin: 20px 0 20px 32px;">没有账号?</a>

                <el-button style="width: 400px;margin: 20px 0 0 25px;" type="primary"
                    @click="submitForm('ruleForm')">提交</el-button>
            </el-col>
            </div>
          
        </el-row>

    </div>
</template>

<script>
export default {
    data() {

        return {
            ruleForm: {
                email: '',
                passWord: '',
                checkCode: ''
            },
            rules: {
                email: [
                    { required: true, message: '请输入您的邮箱', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
                ],
                passWord: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                checkCode: [
                    { required: true, message: '请输入图片验证码', trigger: 'blur' },
                ]
            }
        };
    },
    methods: {
        ale(msg, type = 'info') {//提示消息
            this.$message({
                type: type,
                message: msg,
                center: true
            })
        },
        changeCode() {
            this.$refs.changeCode.src = `/api/check-code?${new Date().getMilliseconds()}`;
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('/user/login',this.ruleForm).then(resp => {
                        this.ale(resp.data.data.message);
                        if (resp.data.code == 10000) {
                            let back = resp.data.data;
                            localStorage.setItem('x-token',back.entity.token);
                            localStorage.setItem('nickName',back.entity.nickName);
                            this.$router.replace('/home');
                        }else{
                            this.changeCode();
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}
</script>

<style lang="scss" scoped>
#login-page {
    height: 100vh;
    background-color:#e9e9e9; 
    display: flex;
    
    .panel{
        background-image: url('../static/joint.png');
        flex: 1;
        background-repeat: no-repeat;
        background-size: cover;
        background-size: 800px;
        background-position-y: center;
        
    }
}

#login-box {
    height: 450px;
    background: white;
    border-radius: 6px;
    margin: 130px 0 0 887px;

}

#login-title {
    margin-bottom: 20px;
    margin-top: 20px;
    font-size: 20px;
    text-align: center;
    font-weight: 700;
    color: #adadad;
}

.el-form-item {
    display: flex;
    justify-content: center;
}

/deep/ .el-form-item__content {
    width: 380px;
    display: flex;
    align-items: center;
}

#check-code-item .el-input {
    width: 200px;

}

/deep/ .el-input__inner {
    height: 55px;
}
/deep/ .el-input__prefix{
    line-height: 55px;
}
</style>