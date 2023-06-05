<template>
    <div>
        <el-button @click="showder = !showder">新建文件夹</el-button>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="" width="500">
                <template slot-scope="scope">
                    <folder-input :shower="showder" @getFolderName="getFolderName"></folder-input>
                </template>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="350">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="250">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import FolderInput from '@/components/FolderInput.vue';
export default {
    components: {
        FolderInput
    },
    methods: {
        getFolderName(name) {
            this.folderName = name;
        }
    },
    data() {
        return {
            folderName: '',
            showder: false,
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }]
        }
    }
}
</script>

<style lang="scss" scoped></style>