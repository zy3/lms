import{v as g,r as u,f as y,o as v,q as P,H as n,R as r,D as m,p as C,j as i,a8 as E,G as q,S as d,az as c,aE as I}from"./index-B4LUeTLg.js";import{t as V}from"./common-CBPeEccM.js";import{P as N}from"./index-BnRV7d3v.js";import{q as S,e as B}from"./book-D9SslOf9.js";const T={style:{margin:"24px auto 0"}},w=g({name:"Mobile",__name:"index",setup(D){let a=u(!1),p=y({bookPositionEnums:[]}),t=u({author:"",authorIndex:"",cipNum:"",classify:"",counts:1,edition:"",isbn:"",name:"",nameIndex:"",position:"",publisher:"",topicIndex:"",translator:""});const b=o=>{a.value=!0,B(o,"add").then(e=>{e.code===200?(a.value=!1,c("保存成功",{type:"success"}),I.push("/emptyPage")):(a.value=!1,c(e.message,{type:"error"}))})};function f(){S().then(o=>{if(o.code===200){let e=o.data,s=V(e.bookPositionEnums);p.bookPositionEnums.push(...s)}}).catch(()=>{})}v(()=>{f()});const _={isbn:[{required:!0,message:"请输入书号"}],position:[{required:!0,message:"请选择书籍位置"}],name:[{required:!0,message:"请输入名称"}],counts:[{required:!0,message:"请输入数量"}]},h=[{label:"书籍位置",prop:"position",valueType:"select",filterable:!0,options:p.bookPositionEnums},{label:"书号",prop:"isbn"},{label:"书名",prop:"name"},{label:"作者",prop:"author"},{label:"译者",prop:"translator"},{label:"出版单位",prop:"publisher"},{label:"出版版次",prop:"edition"},{label:"书名检索词",prop:"nameIndex"},{label:"作者检索词",prop:"authorIndex"},{label:"主题检索词",prop:"topicIndex"},{label:"分类号",prop:"classify"},{label:"CIP核字",prop:"cipNum"},{label:"数量",prop:"counts",valueType:"input-number",fieldProps:{precision:0,step:1}}];return(o,e)=>{const s=m("el-button"),k=m("el-card");return C(),P("div",null,[n(k,null,{default:r(()=>[n(i(N),{modelValue:i(t),"onUpdate:modelValue":e[0]||(e[0]=l=>E(t)?t.value=l:t=l),columns:h,rules:_,labelWidth:"90px",labelPosition:"right",onSubmit:b},{footer:r(({handleSubmit:l,handleReset:x})=>[q("div",T,[n(s,{onClick:x},{default:r(()=>[d("重置")]),_:2},1032,["onClick"]),n(s,{type:"primary",loading:i(a),onClick:l},{default:r(()=>[d("确定")]),_:2},1032,["loading","onClick"])])]),_:1},8,["modelValue"])]),_:1})])}}});export{w as default};
