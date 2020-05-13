<template>
  <div>
    <div class="permissionTool">
      <el-input
        size="small"
        placeholder="请输入角色英文名"
        v-model="role.name"
        >
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input size="small"  placeholder="请输入角色中文名" v-model="role.nameZh">
      </el-input>
      <el-button type="primary" size="small" @click="doAddRole">添加角色</el-button>
    </div>
    <div class="permissionMain">
      <el-collapse
        accordion
        v-model="activeName"
        @change="change">
        <el-collapse-item :title="item.nameZh" :name="item.id" v-for="(item,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix" >
              <span>可访问资源</span>
              <el-button style="float: right; padding: 3px 0;color: #ff0000;" icon="el-icon-delete" type="text" @click="deleteRole(item)"></el-button>
            </div>
            <el-tree
              :data="allmenus"
              ref="tree"
              :props="defaultProps"
              :key="index"
              node-key="id"
              :default-checked-keys="selectedMenus"
              show-checkbox></el-tree>
            <div style="display: flex;justify-content: flex-end">
              <el-button @click="cancelUpdate">取消修改</el-button>
              <el-button type="primary" @click="doUpdate(item.id,index)">确认修改</el-button>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
  export default {
    name: "PermissMana",
    data(){
      return{
        role: {
          name: '',
          nameZh: ''
        },
        roles:[],
        allmenus:[],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        selectedMenus: [],
        activeName:-1
      }
    },
    mounted(){
      this.initRoles()
    },
    methods:{
      deleteRole(role){
        this.$confirm('此操作将永久删除【' + role.nameZh + '】角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/permiss/role/" + role.id).then(resp => {
            if (resp) {
              this.initRoles();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      doAddRole(){
        if(this.role.name && this.role.nameZh){

          this.postRequest('/system/basic/permiss/role',this.role).then(resp=>{
            if (resp){
              this.role.nameZh=''
              this.role.name=''
              this.initRoles()
            }
          })

        }else{
          this.$message('数据不能为空');
        }
      },
      cancelUpdate(){
        this.activeName = -1
      },
      doUpdate(rid,index){
        let tree = this.$refs.tree[index]
        let selectedKeys = tree.getCheckedKeys(true)
        let url = '/system/basic/permiss/?rid='+rid
        selectedKeys.forEach(key=>{
          url += '&mids='+key
        })
        this.putRequest(url).then(resp=>{
          if (resp){
            this.activeName = -1
          }
        })
      },
      change(rid){
        if(rid){
          this.initAllMenus();
          this.initSelectMenus(rid)
        }
      },
      initSelectMenus(rid){
        this.getRequest('/system/basic/permiss/mids/'+rid).then(resp=>{
            if (resp){
              this.selectedMenus = resp
            }
        })
      },
      initAllMenus() {
        this.getRequest("/system/basic/permiss/menus").then(resp => {
          if (resp) {
            this.allmenus = resp;
          }
        })
      },
      initRoles(){
        this.getRequest('/system/basic/permiss/').then(resp=>{
          console.log(resp)
          if (resp){
            this.roles = resp;
          }
        })
      }
    }
  }
</script>

<style>
  .permissionTool{
    display: flex;
    width: 600px;

  }

  .permissManaTool .el-input {
    width: 300px;
    margin-right: 6px;
  }

  .permissionMain{
    text-align: left;
    width: 500px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
</style>
