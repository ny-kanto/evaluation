<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% Etape etape = (Etape) request.getAttribute("etape"); %>
<% List<DetailCoureurEtape> detail = (List<DetailCoureurEtape>) request.getAttribute("detail"); %>
<% List<Equipe> equipe = (List<Equipe>) request.getAttribute("equipe"); %>

<div class="main-content">

    <div class="page-content">
        <div class="container-fluid">

            <!-- start page title -->
            <div class="row">
                <div class="col-12">
                    <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                        <h4 class="mb-sm-0">Donnee</h4>

                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="#">Tables</a></li>
                                <li class="breadcrumb-item active">Donnee</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h4 class="card-title mb-0 flex-grow-1">Etape</h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <% for (Equipe eq : equipe) { %>
                                    <div class="mb-4 ms-2">
                                        <h3 class="fw-bold"><%= eq.getNom() %></h3>
                                        <div class="table-responsive ms-5">
                                            <table class="table align-middle table-nowrap mb-0" id="myTable<%= eq.getId() %>">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Nom</th>
                                                        <th scope="col">Numéro</th>
                                                        <th scope="col">Genre</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% for (DetailCoureurEtape dce : detail) {
                                                        if (eq.getId() == dce.getCoureur().getEquipe().getId()) { %>
                                                            <tr>
                                                                <td><%= dce.getCoureur().getNom() %></td>
                                                                <td><%= dce.getCoureur().getNumeroDossard() %></td>
                                                                <td><%= dce.getCoureur().getGenre() %></td>
                                                            </tr>
                                                        <% }
                                                    } %>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                <% } %>
                                <div class="text-start">
                                    <button type="button" class="btn btn-primary bg-gradient" style="color: white;"><a href="/user/etape/list" style="color: white;">Retour</a> </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end row -->


        </div>
        <!-- container-fluid -->
    </div>
    <%-- <script src="/assets/js/export/jspdf.js"></script> --%>
    <%-- <script src="/assets/js/export/html2pdf.js"></script> --%>
    <%-- <script src="/assets/js/export/xlsx.full.min.js"></script> --%>
    <%-- <script src="/assets/js/export/FileSaver.min.js"></script> --%>
    <%-- <script src="/assets/js/main.js"></script> --%>

    <%@ include file="../footer.jsp" %>