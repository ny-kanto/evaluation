<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<VPointsCoureurEtape> classementEtape = (List<VPointsCoureurEtape>) request.getAttribute("classement_etape"); %>
<% List<Etape> etape = (List<Etape>) request.getAttribute("etape"); %>
<% Etape eta = (Etape) request.getAttribute("e"); %>
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
                <div class="w-auto">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h6 class="card-title mb-0 flex-grow-1">Filtre</h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <div class="row g-4 mb-3">
                                    <form action="/user/classement-etape/list" method="get">
                                        <div class="d-flex">
                                            <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="etape">
                                                <option value="">selectionnez une etape</option>
                                                <% for (Etape e : etape) { %>
                                                    <option value="<%= e.getId() %>"><%= e.getNom() %> <%= e.longueurForm() %></option>
                                                <% } %>
                                            </select>
                                            <div>
                                                <button type="submit" class="btn btn-primary bg-gradient">Filtrer</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h4 class="card-title mb-0 flex-grow-1"><%= eta.getNom() %></h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <div class="table-responsive modal-body-pdf">
                                    <table class="table align-middle table-nowrap mb-0" id="myTable">
                                        <thead>
                                            <tr>
                                                <th scope="col">Rang</th>
                                                <th scope="col">Coureur</th>
                                                <th scope="col">Equipe</th>
                                                <th scope="col">Duree</th>
                                                <th scope="col">Points</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (VPointsCoureurEtape vpce : classementEtape) { %>
                                            <tr>
                                                <td><%= vpce.getRang() %></td>
                                                <td><%= vpce.getNomCoureur() %></td>
                                                <td><%= vpce.getNomEquipe() %></td>
                                                <td><%= vpce.getDuree() %></td>
                                                <td><%= vpce.pointForm() %></td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
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