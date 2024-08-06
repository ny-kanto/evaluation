<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<VClassementGeneral> classement = (List<VClassementGeneral>) request.getAttribute("classement"); %>
<% List<Equipe> equipe = (List<Equipe>) request.getAttribute("equipe"); %>
<% int totalPages = (int) request.getAttribute("totalPages"); %>
<% int noPage = (int) request.getAttribute("noPage"); %>
<% int sort = (int) request.getAttribute("sort"); %>
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
                                    <form action="/admin/classement/list" method="get">
                                        <div class="d-flex">
                                            <div class="me-1">
                                                <input type="number" name="filtre_rang_min" class="form-control w-auto"
                                                    placeholder="Entrez le rang min" />
                                            </div>

                                            <div class="me-1">
                                                <input type="number" name="filtre_rang_max" class="form-control w-auto"
                                                    placeholder="Entrez le rang max" />
                                            </div>

                                            <div class="me-1">
                                                <input type="text" name="filtre_nom" class="form-control w-auto"
                                                    placeholder="Entrez le nom du coureur" />
                                            </div>

                                            <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="filtre_equipe">
                                                <option value="">selectionnez une equipe</option>
                                                <% for (Equipe e : equipe) { %>
                                                    <option value="<%= e.getId() %>"><%= e.getNom() %></option>
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
                            <h4 class="card-title mb-0 flex-grow-1">Etape</h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <div class="row g-4 mb-3">
                                    <div class="col-sm">
                                        <form action="/admin/classement/list" method="get">
                                            <div class="mb-3 d-flex">
                                                <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="nbrParPage">
                                                    <option value="5">5</option>
                                                    <option value="10">10</option>
                                                    <option value="20">20</option>
                                                </select>
                                                <button type="submit" class="btn btn-primary bg-gradient">Valider</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="table-responsive modal-body-pdf">
                                    <table class="table align-middle table-nowrap mb-0" id="myTable">
                                        <thead>
                                            <tr>
                                                <th scope="col">Rang <a href="/admin/classement/list?column=rang&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Coureur <a href="/admin/classement/list?column=nom_coureur&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col"><a href="admin/equipe-classement/list?id_equipe"> Equipe</a> <a href="/admin/classement/list?column=id_equipe&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Point <a href="/admin/classement/list?column=total_points&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (VClassementGeneral vcg : classement) { %>
                                            <tr>
                                                <td><%= vcg.getRang() %></td>
                                                <td><%= vcg.getNomCoureur() %></td>
                                                <td><%= vcg.getNomEquipe() %></td>
                                                <td><%= vcg.totalPointsForm() %></td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="d-flex justify-content-end">
                                    <div class="pagination-wrap hstack gap-2">
                                        <a class="page-item pagination-prev <%= noPage == 1 ? "disabled" : "" %>"
                                            href="<%= noPage == 1 ? "#" : "/admin/classement/list?noPage=" + (noPage - 1) %>"
                                            <%= noPage == 1 ? "disabled" : "" %>>
                                            Previous
                                        </a>
                                        <ul class="pagination mb-0 listjs-pagination">
                                        <% for (int i = 1; i <= totalPages; i++) { %>
                                            <li <% if (i == noPage) { %>class="active"<% } %>><a class="page" href="/admin/classement/list?noPage=<%= i %>"><%= i %></a></li>
                                        <% } %></ul>
                                        <a class="page-item pagination-next <%= noPage == totalPages ? "disabled" : "" %>"
                                            href="<%= noPage == totalPages ? "#" : "/admin/classement/list?noPage=" + (noPage + 1) %>"
                                            <%= noPage == totalPages ? "disabled" : "" %>>
                                            Next
                                        </a>
                                    </div>
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