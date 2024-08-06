<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<VClassementGeneral> classement = (List<VClassementGeneral>) request.getAttribute("classement"); %>
<% Equipe equipe = (Equipe) request.getAttribute("equipe"); %>
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
                            <h4 class="card-title mb-0 flex-grow-1">Equipe <%= equipe.getNom() %></h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <div class="table-responsive modal-body-pdf">
                                    <table class="table align-middle table-nowrap mb-0" id="myTable">
                                        <thead>
                                            <tr>
                                                <%-- <th scope="col">Rang </th> --%>
                                                <th scope="col">Coureur</th>
                                                <th scope="col">Equipe </th>
                                                <th scope="col">Point </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (VClassementGeneral vcg : classement) { %>
                                            <tr>
                                                <%-- <td><%= vcg.getRang() %></td> --%>
                                                <td><%= vcg.getNomCoureur() %></td>
                                                <td><%= vcg.getNomEquipe() %></td>
                                                <td><%= vcg.totalPointsForm() %></td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="text-start">
                                    <button type="button" class="btn btn-primary bg-gradient" style="color: white;"><a href="/admin/classement-equipe/list" style="color: white;">Retour</a> </button>
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