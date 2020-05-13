<template>
  <div style="width: 500px;">
    <el-input
      prefix-icon="el-icon-search"
      placeholder="输入部门或者名称搜索部门..."
      v-model="filterText">
    </el-input>

    <el-tree
      class="filter-tree"
      :data="deps"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      ref="tree">
      <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%;"
            slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button
            type="primary"
            size="mini"
            class="depBtn"
            @click="() => showAddDepView(data)">
            添加
          </el-button>
          <el-button
            type="danger"
            size="mini"
            class="depBtn"
            @click="() => deleteDep(data)">
            删除
          </el-button>
        </span>
      </span>
    </el-tree>

    <el-dialog
      title="添加部门"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handlerDialogClose">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>上级部门</el-tag>
            </td>
            <td style="text-align: left">
              <span >{{pname}}</span>
           </td>
          </tr>
          <tr>
            <td>
              <el-tag>部门名称</el-tag>
            </td>
            <td>
              <el-input v-model="dep.name" placeholder="请输入部门名称..."></el-input>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "DepMana",
    data() {
      return {
        filterText: '',
        deps:[],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        dialogVisible:false,
        dep:{
          parentId:-1,
          name:''
        },
        pname:'',
      };
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    mounted(){
      this.initDeps()
    },
    methods: {
      addDep2Deps(deps,dep){
        for(let i = 0; i<deps.length;i++){
          let d =deps[i]
          if(d.id = dep.parentId){
            d.children = d.children.concat(dep)
            if(d.children.length>0){
              d.parent = true
            }
            return
          }else{
            this.addDep2Deps(d.children,dep)
          }
        }

      },
      doAddDep(){
        if(!this.dep.name){
          this.$message('部门名称不能为空')
          return
        }
        this.postRequest('/system/basic/department/',this.dep).then(resp=>{
          if(resp){
            this.addDep2Deps(this.deps, resp.obj);
            this.dialogVisible = false;
            this.dep = {
              name: '',
              parentId: -1
            }
            this.pname = '';
          }
        })

      },
      removeDepFromDeps(p,deps, id) {
        //动态删除已删除的数据，起到刷新的作用
        for(let i=0;i<deps.length;i++){
          let d = deps[i];
          if (d.id == id) {
            deps.splice(i, 1);
            if (deps.length == 0) {
              p.parent = false;
            }
            return;
          }else{
            this.removeDepFromDeps(d,d.children, id);
          }
        }
      },
      deleteDep(data){
        if(data.parent){
          //如果是父部门则不能删除
          this.$message.error("父部门删除失败")
          return
        }
        this.$confirm('此操作将永久删除'+data.name+'部门, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/system/basic/department/'+data.id).then(resp=>{
            if (resp){
              this.removeDepFromDeps(null,this.deps,data.id);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handlerDialogClose(){
        this.dialogVisible=false
      },
      showAddDepView(data){
        this.pname = data.name
        this.dep.parentId = data.id
        this.dialogVisible=true
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
      },
      initDeps(){
        this.getRequest('/system/basic/department/').then(resp=>{
          if (resp){
            this.deps = resp
          }
        })
      }
    },


  }
</script>

<style>
  .depBtn {
    padding: 2px;
  }
</style>
