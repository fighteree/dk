<template>
    <div>

        <div style="display: none;" ref="show">
            <el-input v-model="folderName" type="text"></el-input>
            <div id="bt">
                <el-button type="success" @click="getFolder" icon="iconfont icon-caiqiequeren"></el-button>
                <el-button type="danger" @click="$refs.show.style.display = 'none'" icon="iconfont icon-quxiao"></el-button>
            </div>
        </div>


    </div>
</template>

<script>
export default {
    data() {
        return {
            folderName: ''
        }
    },
    props: ['shower'],
    methods: {
        createFolder() {
            this.$prompt({
                title: '新建文件夹'
            })
        },
        getFolder() {
            if (this.folderName == '') {
                this.$message({
                    type: 'error',
                    message: '文件夹名不能为空',
                    center: true
                })
                return;
            }
            this.$emit('getFolderName', this.folderName);
            this.$refs.show.style.display = 'none';

        }
    },
    watch: {
        shower() {
            alert(this.shower)
            this.$refs.show.style.display =  'block';
        }
    }
}
</script>

<style lang="scss" scoped>
/deep/ .el-input__inner {
    width: 180px;
    height: 24px;
    line-height: 24px;

}

/deep/ .el-input {
    width: 13%;
}

/deep/ .el-button {
    width: 24px;
    height: 24px;
    padding: 0;

}

#bt {
    display: inline-block;
}
</style>