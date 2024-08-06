<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<Etape> etape = (List<Etape>) request.getAttribute("etape"); %>

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
                                    <form action="/user/etape/list" method="get">
                                        <div class="d-flex">
                                            <div class="me-1">
                                                <input type="text" name="filtre_nom" class="form-control w-auto"
                                                    placeholder="Entrez le nom" />
                                            </div>

                                            <div class="me-1">
                                                <input type="number" name="filtre_longueur_min" class="form-control w-auto"
                                                    placeholder="Entrez la longueur min" />
                                            </div>

                                            <div class="me-1">
                                                <input type="number" name="filtre_longueur_max" class="form-control w-auto"
                                                    placeholder="Entrez la longueur max" />
                                            </div>

                                            <div class="me-1">
                                                <input type="number" name="filtre_nb_coureur_min" class="form-control w-auto"
                                                    placeholder="Entrez le nb coureur min" />
                                            </div>

                                            <div class="me-1">
                                                <input type="number" name="filtre_nb_coureur_max" class="form-control w-auto"
                                                    placeholder="Entrez le nb coureur max" />
                                            </div>
                                            
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
                                        <form action="/user/etape/list" method="get">
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
                                                <th scope="col">Rang <a href="/user/etape/list?column=rang&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Nom <a href="/user/etape/list?column=nom&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Longueur (Km)<a href="/user/etape/list?column=longueur&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Nombre de Coureur par Equipe <a href="/user/etape/list?column=nb_coureur_equipe&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Date depart <a href="/admin/etape/list?column=date_depart&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Heure depart <a href="/admin/etape/list?column=heure_depart&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Etape e : etape) { %>
                                            <tr>
                                                <td><%= e.getRang() %></td>
                                                <td><%= e.getNom() %></td>
                                                <td><%= e.longueurForm() %></td>
                                                <td><%= e.getNbCoureurEquipe() %></td>
                                                <td><%= e.getDateDepart() %></td>
                                                <td><%= e.getHeureDepart() %></td>
                                                <td>
                                                    <div class="hstack gap-3 flex-wrap">
                                                        <a href="/user/etape-coureur/define?id_etape=<%= e.getId() %>" class="link-success fs-15"><i class="ri-pages-line"></i></a>
                                                        <a href="/user/etape/details?id_etape=<%= e.getId() %>" class="link-info fs-15"><i class="ri-file-list-3-line"></i></a>
                                                    </div>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="d-flex justify-content-end">
                                    <div class="pagination-wrap hstack gap-2">
                                        <a class="page-item pagination-prev <%= noPage == 1 ? "disabled" : "" %>"
                                            href="<%= noPage == 1 ? "#" : "/user/etape/list?noPage=" + (noPage - 1) %>"
                                            <%= noPage == 1 ? "disabled" : "" %>>
                                            Previous
                                        </a>
                                        <ul class="pagination mb-0 listjs-pagination">
                                        <% for (int i = 1; i <= totalPages; i++) { %>
                                            <li <% if (i == noPage) { %>class="active"<% } %>><a class="page" href="/user/etape/list?noPage=<%= i %>"><%= i %></a></li>
                                        <% } %></ul>
                                        <a class="page-item pagination-next <%= noPage == totalPages ? "disabled" : "" %>"
                                            href="<%= noPage == totalPages ? "#" : "/user/etape/list?noPage=" + (noPage + 1) %>"
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