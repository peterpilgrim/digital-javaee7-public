<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        XeNoNiQUE :: 2016 :: BASIC JAVA EE 8 MVC
    </title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="XeNoNiQUE">
    <meta name="author" content="Peter Pilgrim">

    <!-- Les Bootstrap styles -->
    <link href="${pageContext.request.contextPath}/styles/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/main.css" rel="stylesheet">
</head>

<body>
<!-- NAVBAR
================================================== -->

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img id="brandNavbarLogo" src="${pageContext.request.contextPath}/images/XeNoNiQUe-Logo-2011-01-233x175-72dpi.png" /></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home<span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Technology <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Digital Java EE 7</a></li>
                        <li><a href="#">Java EE 7</a></li>
                        <li><a href="#">AngularJS</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Peter Pilgrim Blob</a></li>
                        <li class="divider"></li>
                        <li><a href="#">RESTful Services</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="http://en.wikipedia.org/wiki/Digital_marketing">Digital</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Popular <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="main-content">

    <div class="page-header">
        <h1> Handlebars Java EE 8 MVC </h1>
    </div>

    <div class="other-content">
        Person
        <p>
            <a href="${pageContext.request.contextPath}/rest/person" class="btn btn-primary">
                Person (HBS)
            </a>
        </p>
    </div>

    <div class="other-content">
        Product Controller
        <p>
            <a href="${pageContext.request.contextPath}/rest/products/list" class="btn btn-primary">
                List All Products
            </a>
        </p>
    </div>

    <div class="other-content">

        <div class="alert-info" role="alert">
            <p>
                Now make this application work! Do as you please.
            </p>
        </div>

        <p>
            <a href="${pageContext.request.contextPath}/rest/redirect/string" class="btn btn-info"> Redirect #1 </a>
        </p>

        <div>
            <h2>REST GET REQUEST</h2>
            <p>
                <a href="<%= request.getContextPath()%>/rest/sample/hello"> Hyperlink HTTP GET Request to the Sample Endpoint </a>
            </p>
            <p>
                NB: We use JavaServer Pages to gain access to the Servlet Request Context, in order to set up the Hyperlink correctly.
            </p>
        </div>
    </div>



</div>

<div class="container fatbottom">
    <hr class="featurette-divider">
    <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>

        <p>&copy; 2015, 2016 XeNoNiQUe, UK &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
        <p class="slogan"> I can definitely help you.</p>

        <div class="other-content">
            <a href="http://xenonique.co.uk/blog/" title="Peter Pilgrim's Blog">
                <img src="${pageContext.request.contextPath}/images/XeNoNiQUe-Logo-2011-01-233x175-72dpi.png" />
            </a>
        </div>

        <p>
            <a href="https://glassfish.java.net/"> <img src="${pageContext.request.contextPath}/images/glassfish_logo.jpg" width="128" height="65"/> </a>
            <a href="http://twitter.com/peter_pilgrim"> <img src="${pageContext.request.contextPath}/images/twitter-bird-white-on-blue.png" width="64" height="64" /> </a>
            <a href="https://plus.google.com/117513042687634436539/posts"> <img src="${pageContext.request.contextPath}/images/Google-Plus-Logo.png" width="64" height="64" /> </a>
        </p>
    </footer>
</div>

<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
<!-- <script src="http://code.jquery.com/jquery-2.1.3js"></script> -->
<script src="${pageContext.request.contextPath}/javascripts/jquery-2.1.3.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/app/main.js"></script>

</body>
</html>

<!--

=========================================================================================
=========================================================================================
=========================================================================================

Created by Peter Pilgrim (c) 2015 using some most excellent tools:
 On the client side: Twitter Bootstrap, AngularJS and JQuery
 On the server side: Java EE 7 GlassFish 4.1

 Need Development Help? "I can definitely help you"

=========================================================================================
=========================================================================================
=========================================================================================

-->
