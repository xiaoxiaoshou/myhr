<template>
  <div>
    <div class="add">
      <el-input
        size="small"
        class="addPosInput"
        placeholder="添加职位..."
        suffix-icon="el-icon-plus"
        v-model="pos.name">
      </el-input>
      <el-button class="addButton"  icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
    </div>
    <div class="posManaMain">
      <el-table
        :data="positions"
        border
        stripe
        @selection-change="handleSelectionChange"
        size="small"
        style="width: 70%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="职位名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间">
        </el-table-column>
        <el-table-column
          align="left">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="showEditView(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-button @click="deleteMany" type="danger" size="small" class="multipleDelete"
               :disabled="multipleSelection.length==0">批量删除
    </el-button>
    <el-dialog
      title="修改职位"
      :visible.sync="dialogVisible"
      width="30%">
     <div>
       <div>
         <el-tag>职位名称</el-tag>
         <el-input class="updatePosInput" size="small" v-model="updatePos.name"></el-input>
       </div>
     </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doUpdate">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "PoMana",
    data() {
      return {
        pos: {
          name: ''
        },
        positions:[],
        dialogVisible:false,
        updatePos: {
          name: '',
          enabled: false
        },
        multipleSelection: [],
      }
    },
    mounted(){
      this.initPositions();
    }
    ,methods:{
      addPosition(){
        if(this.pos.name){
          this.postRequest('/system/basic/pos/',this.pos).then(resp=>{
            if (resp) {
              this.pos.name =''
              this.initPositions()
            }
          })
        }else{
          this.$message.error('职位名称不可以为空');
        }

      },
      showEditView(index, data){
        this.dialogVisible = true
        this.updatePos = data
      }
      ,
      handleDelete(index, data){
        this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          this.deleteRequest('/system/basic/pos/'+data.id).then(resp=>{
            if(resp){
              this.initPositions();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      deleteMany(){
        this.$confirm('此操作将永久删除'+this.multipleSelection.length+'条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids='?'
          this.multipleSelection.forEach((item,index)=>{
            ids+='ids='+item.id+"&"
          })
          this.deleteRequest('/system/basic/pos/'+ids).then(resp=>{
            if (resp) {
              this.initPositions();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      doUpdate(){
        this.putRequest('/system/basic/pos/',this.updatePos).then(resp=>{
          if(resp){
            this.initPositions();
            this.updatePos.name = '';
            this.dialogVisible = false;
          }
        })
      },
      handleSelectionChange(val){
        this.multipleSelection=val
      },
      initPositions(){
        this.getRequest("/system/basic/pos/").then(resp=>{
          if(resp){
            this.positions = resp
          }
        })
      }
    }
  }
</script>

<style>
  .add{
    display: flex;
  }

.addPosInput{
  width: 300px;
  margin-right: 8px;
  display: inline-flex;
}
  .addButton{
    display: inline-flex;
  }

  .posManaMain{
    margin-top: 10px;
  }

  .updatePosInput {
    width: 200px;
    margin-left: 8px;
  }
  .multipleDelete{
    margin-top: 8px;
    display: flex;

  }

</style>
