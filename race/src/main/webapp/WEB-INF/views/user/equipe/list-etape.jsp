<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<DetailCoureurEtape> list = (List<DetailCoureurEtape>) request.getAttribute("list_detail_equipe"); %>
<% Equipe equipe = (Equipe) request.getAttribute("equipe"); %>
<% List<Etape> etape = (List<Etape>) request.getAttribute("etape"); %>
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
            <!-- end page title -->

            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h4 class="card-title mb-0 flex-grow-1">EQUIPE <%= equipe.getNom() %></h4>
                        </div>
                        <div class="card-body">
                        <% for (Etape e : etape) { %>
                        <div class="mb-5">
                            <!-- Afficher le message d'exception si présent -->
                            <% String exceptionMessage = (String) request.getAttribute("exceptionMessage"); %>
                            <% String etapeErreur = (String) request.getAttribute("etapeErreur"); %>
                            <% if (exceptionMessage != null && etapeErreur != null && etapeErreur.equals(e.getId())) { %>
                                <div class="alert alert-danger" role="alert">
                                    <%= exceptionMessage %>
                                </div>
                            <% } %>
                            <h3><%= e.getNom() %> (<%= e.longueurForm() %>km) - <%= e.getNbCoureurEquipe() %> coureur(s)</h3>
                            <table class="table align-middle table-nowrap mb-0">
                                <thead>
                                    <tr>
                                        <th scope="col">Nom</th>
                                        <th scope="col">Temps Chrono</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Boucle sur les détails des coureurs -->
                                    <% for (DetailCoureurEtape dce : list) {
                                        if (dce.getEtape().getId().equals(e.getId())) { %>
                                        <tr>
                                            <td><%= dce.getCoureur().getNom() %></td>
                                            <td><%= dce.dureeForm() %></td>
                                        </tr>
                                    <% } } %>
                                </tbody>
                            </table>
                            <button type="button" class="btn btn-success add-btn bg-gradient mt-3"><a href="/user/equipe-etape/form?id_etape=<%= e.getId() %>" style="color:white;"><i class="ri-add-line align-bottom me-1"></i> Ajouter coureur<a/></button>
                        </div>
                        <% } %>
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