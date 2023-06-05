<template>
    <div>
        <el-row id="page" style="height: 100vh;">
            <el-col id="login-box" :span="7">
                <div id="login-title">
                    DK网盘
                </div>

                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="0px"
                    class="demo-ruleForm">

                    <el-form-item prop="email">
                        <el-input type="text" v-model="ruleForm.email" placeholder="请输入您的邮箱" autocomplete="off">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-youxiang"></i>
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="emailCode" id="email-code-input">
                        <el-input type="text" id="input-width" placeholder="请输入邮箱验证码" v-model="ruleForm.emailCode"
                            autocomplete="off">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                        <el-button style="margin-left: 5px;" type="primary" :disabled="disabled" @click="sendEmail">{{
                            codeText }}</el-button>
                    </el-form-item>

                    <el-form-item prop="nickName">
                        <el-input prefix-icon="el-icon-s-custom" v-model="ruleForm.nickName" placeholder="请输入昵称"></el-input>
                    </el-form-item>

                    <el-form-item prop="passWord">
                        <el-input type="passWord" :show-password="true" v-model="ruleForm.passWord" placeholder="请输入密码">
                            <i style="font-size: 20px;color: black;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="confirmPwd">
                        <el-input type="passWord" :show-password="true" v-model="ruleForm.confirmPwd" placeholder="请再次输入密码">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="checkCode" id="img-item">
                        <el-input type="text" id="img-input-width" v-model="ruleForm.checkCode" placeholder="图片验证码">
                            <i style="font-size: 20px;" slot="prefix" class="iconfont icon-anquanbaozhang"></i>
                        </el-input>
                        <img src="/api/check-code" ref="checkCode" @click="changeCheckCode" style="width: 110px;"
                            alt="网络不佳">
                        <a href="#" style="text-decoration: none;margin-left: 30px;font-size: 15px;margin-top: 8px;"
                            @click="changeCheckCode">换一张?</a>
                    </el-form-item>

                    <a href="#/login" style="margin-left:32px;text-decoration: none;">已有账号?</a>
                    <el-button type="primary" style="width: 380px;margin: 20px 0 0 32px;"
                        @click="submitForm('ruleForm')">立即注册</el-button>
                </el-form>

            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    data() {
        //密码校验
        var passWordCheck = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.passWord) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        }

        return {
            ruleForm: {
                email: '',
                emailCode: '',
                nickName: '',
                passWord: '',
                checkCode: '',
                confirmPwd: ''
            },
            disabled: false,
            count: '60',
            codeText: '获取邮箱验证码',
            rules: {
                email: [
                    { required: true, message: '请填写您的邮箱', trigger: 'blur' },
                    { type: 'email', message: '请填写正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                emailCode: [
                    { required: true, message: '请填写邮箱验证码', trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '请设置您的昵称', trigger: 'blur' },
                    { min: 3, max: 15, message: '昵称长度在3到15个字符之间', trigger: ['blur', 'change'] },
                    { pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]+$/, message: '昵称只能包含中英文、数字', trigger: 'blur' }
                ],
                passWord: [
                    { required: true, message: '请设置您的登录密码', trigger: 'blur' },
                    { min: 8, max: 20, message: '密码长度在8-20位之间', trigger: ['blur', 'change'] },
                    { pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]+$/, message: '密码只能包含中英文、数字', trigger: ['blur', 'change'] }
                ],
                confirmPwd: [
                    { validator: passWordCheck, trigger: 'blur' }
                ],
                checkCode: [
                    { required: true, message: '请填写图片验证码', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        submitForm(formName) {//提交
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('/user/register', this.ruleForm).then(resp => {
                        if (resp.data.code == 10000) {
                            this.$message({
                                type: 'seccess',
                                message: '注册成功',
                                center: true
                            })
                        } else {
                            this.$message({
                                type: 'error',
                                message: resp.data.data.message,
                                center: true
                            })
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        changeCheckCode() {//切换验证码图片

            this.$refs.checkCode.src = `/api/check-code?${new Date().getMilliseconds()}`;
        },
        sendEmail() {//发送邮箱验证按
            if (this.disabled) {
                return;
            }

            this.$refs.ruleForm.validateField(['email'], valid => {
                if (!valid) {
                    axios.get('/send-email', {
                        params: {
                            email: this.ruleForm.email,
                            type: 0
                        }
                    }).then(resp => {
                        if (resp.data.code == 10000) {
                            this.disabled = true;
                            let timerTask = setInterval(() => {
                                if (this.count < 1) {
                                    this.disabled = false;
                                    this.count = '60';
                                    this.codeText = '获取邮箱验证码';
                                    clearInterval(timerTask);
                                } else {
                                    this.codeText = `${--this.count}后重新发送`;
                                }
                            }, 1000)


                        } else {
                            this.$message({
                                type: 'error',
                                message: resp.data.data.message,
                                center: true
                            })
                            this.changeCheckCode();//改变验证码
                        }
                    })
                } else {
                    return false;
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
#login-box {
    background-color: #ffffff;
    height: 600px;
    margin: 110px 0 0 930px;
    border-radius: 6px;
}

#login-title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 28px;
    margin-top: 25px;
    font-weight: 700;
    color: #a6a6a6;
}

.el-form-item {
    display: flex;
    align-content: center;
    justify-content: center;
}

#page {
    background-image: url('../static/login-bg.jpg');
}



#input-width {
    width: 250px !important;

}

#img-item .el-input {
    width: 40%;
}

/deep/ .el-form-item__content {
    width: 380px;
    height: 48px;
    display: flex;
    align-items: center;

}

#email-code-input .el-input {
    width: 67%;
}

#img-input-width {
    width: 150px;


}
</style>