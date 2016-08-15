<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Object user=session.getAttribute("user");
   String currentuser="{}";
   if(user!=null){//未登录，跳转登录
      currentuser=((JSONObject)user).toJSONString();
   }
%>
<!DOCTYPE html>
<html data-ng-app="angle">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
   <meta name="description" content="">
   <meta name="keywords" content="unail alienlab">
   <title>AlienLabAdmin</title>
   <!-- Bootstrap styles-->
   <link rel="stylesheet" href="app/css/bootstrap.css">
   <link rel="stylesheet" href="vendor/angularjs/angular-material.min.css">
   <!-- Application styles-->
   <link rel="stylesheet" href="app/css/app.css">
   <!-- Themes-->
   <link rel="stylesheet" ng-href="{{app.layout.theme}}" data-ng-if="app.layout.theme">
   <style>
      md-sidenav {
         box-sizing: border-box;
         position: fixed !important;
         -webkit-flex-direction: column;
         -ms-flex-direction: column;
         flex-direction: column;
         z-index: 60;
         max-height: 100%;
         box-sizing: border-box;
         padding-top:55px;
         max-width: 1200px !important;
         bottom: 0;
         overflow: auto;
         -webkit-overflow-scrolling: touch;
      }
   </style>
   <script>
      var CURRENTUSER=<%=currentuser%>;
   </script>
</head>

<body data-ng-class="{ 'layout-fixed' : app.layout.isFixed, 'aside-collapsed' : app.layout.isCollapsed, 'layout-boxed' : app.layout.isBoxed, 'layout-fs': app.useFullLayout, 'hidden-footer': app.hiddenFooter, 'layout-h': app.layout.horizontal, 'aside-float': app.layout.isFloat, 'offsidebar-open': app.offsidebarOpen, 'aside-toggled': app.asideToggled}">
   <div data-preloader></div>
   <div data-ui-view="" data-autoscroll="false" class="wrapper"></div>
   <script src="app/js/base.js"></script>
   <script src="vendor/angularjs/angular-animate.min.js"></script>
   <script src="vendor/angularjs/angular-aria.min.js"></script>
   <script src="vendor/angularjs/angular-material.min.js"></script>
   <script src="app/js/app.js"></script>
   <script src="system/system.js"></script>
</body>

</html>