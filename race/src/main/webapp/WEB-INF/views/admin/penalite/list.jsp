<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% List<Penalite> penalite = (List<Penalite>) request.getAttribute("penalite"); %>
<% List<Etape> etape = (List<Etape>) request.getAttribute("etape"); %>
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
                                    <form action="/admin/penalite/list" method="get">
                                        <div class="d-flex">
                                            <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="filtre_etape">
                                                <option value="">selectionnez une etape</option>
                                                <% for (Etape e : etape) { %>
                                                    <option value="<%= e.getId() %>"><%= e.getNom() %> <%= e.longueurForm() %> km</option>
                                                <% } %>
                                            </select>

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
                            <h4 class="card-title mb-0 flex-grow-1">Penalite</h4>
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
                                        <form action="/admin/penalite/list" method="get">
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
                                                <th scope="col">Etape (Longueur km)<a href="/admin/penalite/list?column=id_etape&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Equipe <a href="/admin/penalite/list?column=id_equipe&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Temps <a href="/admin/penalite/list?column=temps&sort=<%= (sort+1) %>"><i class="ri-expand-up-down-line" style="float: right;"></i></a></th>
                                                <th scope="col">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Penalite p : penalite) { %>
                                            <tr>
                                                <td><%= p.getEtape().getNom() %> (<%= p.getEtape().longueurForm() %>)</td>
                                                <td><%= p.getEquipe().getNom() %></td>
                                                <td><%= p.getTemps() %></td>
                                                <td>
                                                    <div class="hstack gap-3 flex-wrap">
                                                        <a href="#" data-bs-toggle="modal" data-penalite-delete-id="<%= p.getId() %>" class="link-danger fs-15"
                                                            data-bs-target="#deleteModal"><i class="ri-delete-bin-line"></i></a>
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
                                            href="<%= noPage == 1 ? "#" : "/admin/penalite/list?noPage=" + (noPage - 1) %>"
                                            <%= noPage == 1 ? "disabled" : "" %>>
                                            Previous
                                        </a>
                                        <ul class="pagination mb-0 listjs-pagination">
                                        <% for (int i = 1; i <= totalPages; i++) { %>
                                            <li <% if (i == noPage) { %>class="active"<% } %>><a class="page" href="/admin/penalite/list?noPage=<%= i %>"><%= i %></a></li>
                                        <% } %></ul>
                                        <a class="page-item pagination-next <%= noPage == totalPages ? "disabled" : "" %>"
                                            href="<%= noPage == totalPages ? "#" : "/admin/penalite/list?noPage=" + (noPage + 1) %>"
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
                        <form action="/admin/penalite/save" method="post">
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="etape-field" class="form-label">Etape</label>
                                    <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="etape">
                                        <option value="">selectionnez une etape</option>
                                        <% for (Etape e : etape) { %>
                                            <option value="<%= e.getId() %>"><%= e.getNom() %> <%= e.longueurForm() %> km</option>
                                        <% } %>
                                    </select>
                                    <div class="invalid-feedback">Entrez l'etape.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="equipe-field" class="form-label">Equipe</label>
                                    <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="equipe">
                                        <option value="">selectionnez une equipe</option>
                                        <% for (Equipe e : equipe) { %>
                                            <option value="<%= e.getId() %>"><%= e.getNom() %></option>
                                        <% } %>
                                    </select>
                                    <div class="invalid-feedback">Entrez l'equipe.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="temps-field" class="form-label">Temps</label>
                                    <input type="time" step="1" name="temps" id="temps-field" class="form-control"
                                        placeholder="Entrez le temps" required />
                                    <div class="invalid-feedback">Entrez le temps.</div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="justify-content-end">
                                    <button type="button" class="btn btn-light"
                                        data-bs-dismiss="modal">Fermer</button>
                                    <button type="submit" class="btn btn-success">Ajouter penalite</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


            <!-- Modal de suppression -->
            <div class="modal fade zoomIn" id="deleteModal" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                                id="btn-close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mt-2 text-center">
                                <lord-icon src="https://cdn.lordicon.com/gsqxdxog.json"
                                    colors="primary:#f7b84b,secondary:#f06548"
                                    style="width:100px;height:100px"></lord-icon>
                                <div class="mt-4 pt-2 fs-15 mx-4 mx-sm-5">
                                    <h4>Êtes-vous sûrs ?</h4>
                                    <p class="text-muted mx-4 mb-0">Vous êtes sûrs de supprimer cette ligne ?</p>
                                </div>
                            </div>
                            <div class="d-flex gap-2 justify-content-center mt-4 mb-2">
                                <button type="button" class="btn w-sm btn-light"
                                    data-bs-dismiss="modal">Fermer</button>
                                <button type="button" class="btn w-sm btn-danger" id="delete-penalite">Oui,
                                    Supprime-la!</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- container-fluid -->
    </div>
    <script>
        var deleteLink = document.getElementById("delete-penalite");

        var modal = document.getElementById("deleteModal");

        modal.addEventListener("shown.bs.modal", function(event) {
            var triggerElement = event.relatedTarget;

            var penaliteId = triggerElement.getAttribute("data-penalite-delete-id");

            deleteLink.addEventListener("click", function(event) {
                event.preventDefault();

                console.log("id : " + penaliteId);

                window.location.href = "http://127.0.0.1:8080/admin/penalite/delete?id_penalite=" + penaliteId;

                console.log("url : " + window.location.href);
            });
        });
    </script>
    <%-- <script src="/assets/js/export/jspdf.js"></script> --%>
    <%-- <script src="/assets/js/export/html2pdf.js"></script> --%>
    <%-- <script src="/assets/js/export/xlsx.full.min.js"></script> --%>
    <%-- <script src="/assets/js/export/FileSaver.min.js"></script> --%>
    <%-- <script src="/assets/js/main.js"></script> --%>

    <%@ include file="../footer.jsp" %>