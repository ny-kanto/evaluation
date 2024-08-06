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
                                    <form action="/admin/etape/list" method="get">
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
                                    <div class="col-sm-auto">
                                        <div>
                                            <button type="button" class="btn btn-success add-btn bg-gradient"
                                                data-bs-toggle="modal" id="create-btn"
                                                data-bs-target="#showAddModal"><i
                                                    class="ri-add-line align-bottom me-1"></i> Ajouter</button>
                                        </div>
                                    </div>
                                    <div class="col-sm">
                                        <form action="/admin/etape/list" method="get">
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
                                                <th scope="col">Rang <a href="/admin/etape/list?column=rang&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Nom <a href="/admin/etape/list?column=nom&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Longueur (Km)<a href="/admin/etape/list?column=longueur&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Nb Coureur par Equipe <a href="/admin/etape/list?column=nb_coureur_equipe&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
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
                                                        <a href="/admin/etape-coureur/define?id_etape=<%= e.getId() %>" class="link-success fs-15"><i class="ri-pages-line"></i></a>
                                                        <a href="/admin/etape/details?id_etape=<%= e.getId() %>" class="link-info fs-15"><i class="ri-pages-line"></i></a>
                                                        <a href="/admin/etape/list-classement?id_etape=<%= e.getId() %>" class="link-success fs-15">Resultats</a>
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
                                            href="<%= noPage == 1 ? "#" : "/admin/etape/list?noPage=" + (noPage - 1) %>"
                                            <%= noPage == 1 ? "disabled" : "" %>>
                                            Previous
                                        </a>
                                        <ul class="pagination mb-0 listjs-pagination">
                                        <% for (int i = 1; i <= totalPages; i++) { %>
                                            <li <% if (i == noPage) { %>class="active"<% } %>><a class="page" href="/admin/etape/list?noPage=<%= i %>"><%= i %></a></li>
                                        <% } %></ul>
                                        <a class="page-item pagination-next <%= noPage == totalPages ? "disabled" : "" %>"
                                            href="<%= noPage == totalPages ? "#" : "/admin/etape/list?noPage=" + (noPage + 1) %>"
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

            <!-- Modal d'insertion -->
            <div class="modal fade" id="showAddModal" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-light p-3">
                            <h5 class="modal-title" id="exampleModalLabel"></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                                id="close-modal"></button>
                        </div>
                        <form action="/admin/etape/save" method="post">
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="nom-field" class="form-label">Nom</label>
                                    <input type="text" name="nom" id="nom-field" class="form-control"
                                        placeholder="Entrez le nom" required />
                                    <div class="invalid-feedback">Entrez le nom.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="longueur-field" class="form-label">Longueur</label>
                                    <input type="text" name="longueur" id="longueur-field" class="form-control"
                                        placeholder="Entrez la longueur" required />
                                    <div class="invalid-feedback">Entrez la longueur.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="nb-coureur-field" class="form-label">Nombre de coureur</label>
                                    <input type="number" name="nb_coureur" id="nb-coureur-field" class="form-control"
                                        placeholder="Entrez le nombre coureur" required />
                                    <div class="invalid-feedback">Entrez le nombre coureur.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="rang-field" class="form-label">Rang</label>
                                    <input type="number" name="rang" id="rang-field" class="form-control"
                                        placeholder="Entrez le rang" required />
                                    <div class="invalid-feedback">Entrez le rang.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="date-depart-field" class="form-label">Date depart</label>
                                    <input type="date" name="date_depart" id="date-depart-field" class="form-control"
                                        placeholder="Entrez la date depart" required />
                                    <div class="invalid-feedback">Entrez la date depart.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="heure-depart-field" class="form-label">Heure depart</label>
                                    <input type="time" name="heure_depart" id="heure-depart-field" class="form-control" step="1"
                                        placeholder="Entrez l'heure depart" required />
                                    <div class="invalid-feedback">Entrez l'heure depart.</div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="justify-content-end">
                                    <button type="button" class="btn btn-light"
                                        data-bs-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success">Ajouter l'etape</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <!-- container-fluid -->
    </div>
    <%-- <script src="/assets/js/export/jspdf.js"></script> --%>
    <%-- <script src="/assets/js/export/html2pdf.js"></script> --%>
    <%-- <script src="/assets/js/export/xlsx.full.min.js"></script> --%>
    <%-- <script src="/assets/js/export/FileSaver.min.js"></script> --%>
    <%-- <script src="/assets/js/main.js"></script> --%>

    <%@ include file="../footer.jsp" %>