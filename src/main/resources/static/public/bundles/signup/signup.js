const __vite__mapDeps=(i,m=__vite__mapDeps,d=(m.f||(m.f=["shared/Navbar-chunk-Bmq4wKz-.js","shared/index-chunk-CEXPDuLj.js","shared/index-DnVUw_vK.css","shared/hooks-chunk-DtK45pyX.js","shared/redirect-chunk-DBXMV5M2.js","shared/Alert-chunk-7KL-PA51.js","shared/Fade-chunk-BUf0lCBf.js","shared/divWithClassName-chunk-CAIrAdFE.js","shared/CloseButton-chunk-481vGlg4.js","shared/AbstractModalHeader-chunk-Z9zsovdD.js","shared/hasClass-chunk-CnlNRzFA.js","shared/warning-chunk-C3XMKpUZ.js","shared/CardHeaderContext-chunk-DUDyEd_j.js","shared/InputGroupContext-chunk-DiYP6hY5.js","shared/Navbar-DjE7EqfI.css","shared/Footer-chunk-B0nAH3Vv.js","shared/html.parse-chunk-DPn1M0Cf.js","shared/Footer-IHQMiBN9.css"])))=>i.map(i=>d[i]);
import{f as K,r as d,j as a,C as Q,t as G,P as U,B as W,_ as $,o as X,L as Y,l as aa,n as ea,I as sa,S as ra,p as oa,q as ta}from"../shared/index-chunk-CEXPDuLj.js";import{A as na}from"../shared/anonymousRoot.store-chunk-Bftr8kj0.js";/* empty css                */import{c as q,a as la,b as h,d as ia,F as da}from"../shared/index.esm-chunk-BR9CWGfH.js";import{P as A}from"../shared/PasswordToggleIcon-chunk-BrqBsq4A.js";import{r as ma}from"../shared/redirect-chunk-DBXMV5M2.js";import{R as i}from"../shared/Row-chunk-DhIDnGHf.js";import{a as o}from"../shared/Form-chunk-MwMlHtPH.js";import"../shared/warning-chunk-C3XMKpUZ.js";const ca=e=>q().shape({firstName:h().matches(new RegExp("^\\p{Lu}\\p{L}*(?:[ '-]\\p{Lu}?\\p{L}*)*$","u"),e==null?void 0:e["validation.name.regex"]).required(e==null?void 0:e["validation.firstName"]),lastName:h().matches(new RegExp("^\\p{Lu}\\p{L}*(?:[ '-]\\p{Lu}?\\p{L}*)*$","u"),e==null?void 0:e["validation.name.regex"]).required(e==null?void 0:e["validation.lastName"]),email:h().email(e==null?void 0:e["validation.email"]).required(e==null?void 0:e["validation.email"]),pwdForm:q().shape({password:h().matches(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])\S{8,}$/,e==null?void 0:e["validation.password"]).required(e==null?void 0:e["validation.password"]),confirmPassword:h().oneOf([ia("password"),void 0],e==null?void 0:e["validation.confirmPassword"]).required(e==null?void 0:e["validation.confirmPassword"])}),tosChecked:la().isTrue(e==null?void 0:e["validation.tos"]).required(e==null?void 0:e["validation.tos"])}),p=e=>a.jsx(o.Control.Feedback,{type:"invalid",tooltip:!0,children:e}),pa=()=>{const e=R(),{uiStore:{bundle:s,pathNavigation:m,assets:u}}=e,[z,w]=d.useState(!1),[O,x]=d.useState(!1),[T,f]=d.useState(!1),[D,j]=d.useState(!1),[H,g]=d.useState(!1),[N,M]=d.useState(!1),[v,Z]=d.useState(!1),J=c=>{e.loading=!0,(async(l,t)=>{if(t)return K.post(t,l)})(c,m==null?void 0:m.signupSubmit).then(l=>{e.loading=!1,l&&ma(l)})},F=(c,l)=>!!(c!=null&&c[l]);return a.jsx(Q,{className:"form-container",children:a.jsxs(i,{className:"form-content",children:[a.jsx(G,{className:"d-flex justify-content-center align-items-center",style:{backgroundColor:"#f8f9fa"},md:6,children:a.jsx("div",{className:"p-3",children:a.jsx("img",{src:`${U}${u==null?void 0:u.chatBot}`,alt:"ChatBot",className:"chatbot-img"})})}),a.jsxs(G,{className:"p-3",children:[a.jsx(i,{children:a.jsx("h3",{className:"signup-title",children:s==null?void 0:s["page.createAccount"]})}),a.jsx(i,{children:a.jsx(da,{initialValues:{firstName:"",lastName:"",email:"",pwdForm:{password:"",confirmPassword:""},tosChecked:!1},validationSchema:ca(s),onSubmit:c=>{J(c)},children:({handleSubmit:c,handleChange:l,values:t,touched:n,errors:r})=>{var C,P,S,b,I,L,y,k,_,B,V,E;return a.jsxs(o,{noValidate:!0,onSubmit:c,children:[a.jsxs(i,{children:[a.jsxs(o.Group,{className:"col-lg-6 input-wrapper "+(z?"focused":""),controlId:"firstName",children:[a.jsx(o.Label,{className:"floating-label",children:s==null?void 0:s["page.enterFirstName"]}),a.jsx(o.Control,{name:"firstName",type:"text",value:t.firstName,onChange:l,onFocus:()=>w(!0),onBlur:()=>w(t.firstName!==""),isValid:n.firstName&&!r.firstName,isInvalid:n.firstName&&!!r.firstName}),p(r==null?void 0:r.firstName)]}),a.jsxs(o.Group,{className:"col-lg-6 input-wrapper "+(O?"focused":""),controlId:"lastName",children:[a.jsx(o.Label,{className:"floating-label",children:s==null?void 0:s["page.enterLastName"]}),a.jsx(o.Control,{name:"lastName",type:"text",value:t.lastName,onChange:l,onFocus:()=>x(!0),onBlur:()=>x(t.lastName!==""),isValid:n.lastName&&!r.lastName,isInvalid:n.lastName&&!!r.lastName}),p(r==null?void 0:r.lastName)]})]}),a.jsx(i,{children:a.jsxs(o.Group,{className:"input-wrapper "+(T?"focused":""),controlId:"email",children:[a.jsx(o.Label,{className:"floating-label",children:s==null?void 0:s["page.enterEmail"]}),a.jsx(o.Control,{name:"email",type:"email",value:t.email,onChange:l,onFocus:()=>f(!0),onBlur:()=>f(t.email!==""),isValid:n.email&&!r.email,isInvalid:n.email&&!!r.email}),p(r==null?void 0:r.email)]})}),a.jsx(i,{children:a.jsxs(o.Group,{className:"input-wrapper "+(D?"focused":""),controlId:"password",children:[a.jsx(o.Label,{className:"floating-label",children:s==null?void 0:s["page.enterPassword"]}),a.jsx(o.Control,{name:"pwdForm.password",type:N?"text":"password",value:((C=t.pwdForm)==null?void 0:C.password)||"",onChange:l,onFocus:()=>j(!0),onBlur:()=>j(t.pwdForm.password!==""),isValid:((P=n.pwdForm)==null?void 0:P.password)&&!((S=r.pwdForm)!=null&&S.password),isInvalid:((b=n.pwdForm)==null?void 0:b.password)&&!!((I=r.pwdForm)!=null&&I.password)}),!F(n.pwdForm,"password")&&a.jsx(A,{showPassword:N,setShowPassword:M,rightPadding:20}),p((L=r==null?void 0:r.pwdForm)==null?void 0:L.password)]})}),a.jsx(i,{children:a.jsxs(o.Group,{className:"input-wrapper "+(H?"focused":""),controlId:"confirmPassword",children:[a.jsx(o.Label,{className:"floating-label",children:s==null?void 0:s["page.confirmPassword"]}),a.jsx(o.Control,{name:"pwdForm.confirmPassword",type:v?"text":"password",value:((y=t.pwdForm)==null?void 0:y.confirmPassword)||"",onChange:l,onFocus:()=>g(!0),onBlur:()=>g(t.pwdForm.confirmPassword!==""),isValid:((k=n.pwdForm)==null?void 0:k.confirmPassword)&&!((_=r.pwdForm)!=null&&_.confirmPassword),isInvalid:((B=n.pwdForm)==null?void 0:B.confirmPassword)&&!!((V=r.pwdForm)!=null&&V.confirmPassword)}),!F(n.pwdForm,"confirmPassword")&&a.jsx(A,{showPassword:v,setShowPassword:Z,rightPadding:20}),p((E=r==null?void 0:r.pwdForm)==null?void 0:E.confirmPassword)]})}),a.jsx(i,{children:a.jsxs(o.Group,{controlId:"tosChecked",children:[a.jsx(o.Check,{name:"tosChecked",type:"checkbox",label:a.jsxs("p",{children:[s==null?void 0:s["page.tosStart"]," ",a.jsx("a",{href:(m==null?void 0:m.tos)||"",children:s==null?void 0:s["page.tosEnd"]})]}),checked:t.tosChecked,onChange:l,isValid:n.tosChecked&&!r.tosChecked,isInvalid:n.tosChecked&&!!r.tosChecked}),p(r==null?void 0:r.tosChecked)]})}),a.jsx(i,{children:a.jsx(o.Group,{children:a.jsx(W,{variant:"primary",type:"submit",className:"form-button",children:s==null?void 0:s["page.signupButton"]})})}),a.jsx(i,{children:a.jsx(o.Group,{children:a.jsxs("p",{className:"mt-3 text-center",children:[s==null?void 0:s["page.alreadyHaveAccount"]," ",a.jsx("a",{href:(m==null?void 0:m.login)||"",children:s==null?void 0:s["page.logIn"]})]})})})]})}})})]})]})})},ha=d.lazy(()=>$(()=>import("../shared/Navbar-chunk-Bmq4wKz-.js"),__vite__mapDeps([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]))),ua=d.lazy(()=>$(()=>import("../shared/Footer-chunk-B0nAH3Vv.js"),__vite__mapDeps([15,1,2,16,17]))),wa=X(function(){const e=R(),{uiStore:s}=e;return a.jsx(a.Fragment,{children:e.loading?a.jsx(Y,{}):a.jsxs(a.Fragment,{children:[a.jsx(ha,{uiStore:s}),a.jsx(pa,{}),a.jsx(ua,{uiStore:s})]})})}),{useStoreContext:R,StoreContext:xa}=aa(),fa=ta(xa),ja=new na(()=>ea(ra,sa));oa.createRoot(document.getElementById("root")).render(a.jsx(d.StrictMode,{children:a.jsx(fa,{rootStore:ja,children:a.jsx(wa,{})})}));
