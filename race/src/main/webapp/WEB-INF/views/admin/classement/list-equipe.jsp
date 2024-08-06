<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List, com.google.gson.Gson" %>
<%@ include file="../header.jsp" %>
<% List<VPointsCoureurEtape> classementEquipe = (List<VPointsCoureurEtape>) request.getAttribute("classement_equipe"); %>
<% List<Categorie> categorie = (List<Categorie>) request.getAttribute("categorie"); %>
<% Categorie cate = (Categorie) request.getAttribute("c"); %>
<% List<Integer> listRang = (List<Integer>) request.getAttribute("rang"); %>
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
                                    <form action="/admin/classement-equipe/list" method="get">
                                        <div class="d-flex">
                                            <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="categorie">
                                                <option value="">selectionnez une categorie</option>
                                                <% for (Categorie e : categorie) { %>
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
                <div class="col-lg-8">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h4 class="card-title mb-0 flex-grow-1"><%= cate.getNom() %></h4>
                        </div>
                        <div class="card-body">
                            <div class="live-preview">
                                <div class="table-responsive modal-body-pdf">
                                    <table class="table align-middle table-nowrap mb-0" id="myTable">
                                        <thead>
                                            <tr>
                                                <th scope="col">Rang</th>
                                                <th scope="col">Equipe</th>
                                                <th scope="col">Points</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (VPointsCoureurEtape vpce : classementEquipe) { %>
                                                <% if(listRang != null) { %>
                                                    <% if(listRang.size() > 0 && cate.getId() != null) { %>
                                                        <% for (Integer rang: listRang) { 
                                                        if (rang == vpce.getRang()) { %>
                                                        <tr style="<%= rang == vpce.getRang() ? "background-color: red;" : "" %>">
                                                            <td><%= vpce.getRang() %></td>
                                                            <td><%= vpce.getNomEquipe() %></td>
                                                            <td><%= vpce.pointForm() %></td>
                                                            <% if (vpce.getRang() == 1 && cate.getId() == null) { %>
                                                                <td>
                                                                    <div>
                                                                        <button type="button" class="btn btn-success add-btn bg-gradient"><a href="/admin/classement-equipe/certificat?id_equipe=<%= vpce.getIdEquipe() %>" style="color: white;">Voir Certificat PDF</a></button>
                                                                    </div>
                                                                </td>
                                                            <% } %>
                                                        </tr>
                                                        <% } else { %>
                                                            <tr">
                                                                <td><%= vpce.getRang() %></td>
                                                                <td><%= vpce.getNomEquipe() %></td>
                                                                <td><%= vpce.pointForm() %></td>
                                                                <% if (vpce.getRang() == 1 && cate.getId() == null) { %>
                                                                    <td>
                                                                        <div>
                                                                            <button type="button" class="btn btn-success add-btn bg-gradient"><a href="/admin/classement-equipe/certificat?id_equipe=<%= vpce.getIdEquipe() %>" style="color: white;">Voir Certificat PDF</a></button>
                                                                        </div>
                                                                    </td>
                                                                <% } %>
                                                            </tr>
                                                        <% } %>
                                                        <% break;} %>
                                                    <% } else if (listRang.size() == 0 && cate.getId() != null) { %>
                                                    <tr>
                                                        <td><%= vpce.getRang() %></td>
                                                        <td><a href="/admin/coureur-classement/list?id_equipe=<%= vpce.getIdEquipe() %>" ><%= vpce.getNomEquipe() %></a></td>
                                                        <td><%= vpce.pointForm() %></td>
                                                        <% if (vpce.getRang() == 1 && cate.getId() == null) { %>
                                                            <td>
                                                                <div>
                                                                    <button type="button" class="btn btn-success add-btn bg-gradient"><a href="/admin/classement-equipe/certificat?id_equipe=<%= vpce.getIdEquipe() %>" style="color: white;">Voir Certificat PDF</a></button>
                                                                </div>
                                                            </td>
                                                        <% } %>
                                                    </tr>
                                                <% } %>
                                            <% } else { %>
                                                    <tr>
                                                        <td><%= vpce.getRang() %></td>
                                                        <td><a href="/admin/coureur-classement/list?id_equipe=<%= vpce.getIdEquipe() %>" ><%= vpce.getNomEquipe() %></a></td>
                                                        <td><%= vpce.pointForm() %></td>
                                                        <% if (vpce.getRang() == 1 && cate.getId() == null) { %>
                                                            <td>
                                                                <div>
                                                                    <button type="button" class="btn btn-success add-btn bg-gradient"><a href="/admin/classement-equipe/certificat?id_equipe=<%= vpce.getIdEquipe() %>" style="color: white;">Voir Certificat PDF</a></button>
                                                                </div>
                                                            </td>
                                                        <% } %>
                                                    </tr>
                                            <% } %>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="card">
                        <canvas id="Chart2" class="chartjs" data-height="400" height="400"></canvas>
                    </div>
                </div>
            </div>
            <!-- end row -->
        </div>
        <!-- container-fluid -->
    </div>

    <script src="/assets/js/chart.min.js"></script>
    <script>
        var dataJson = <%= new Gson().toJson(classementEquipe) %>;
        console.log(dataJson);
        const ctx2 = document.getElementById("Chart2").getContext("2d");
        const chart2 = new Chart(ctx2, {
            type: "doughnut",
            data: {
                labels: dataJson.map(data => data.nomEquipe),
                datasets: [{
                    label: "",
                    backgroundColor: [
                        "#FF5733", "#FFC300", "#DAF7A6", "#C70039", "#3399FF"
                    ],
                    data: dataJson.map(data => data.point),
                }],
            },
            options: {
                animation: {
                    duration: 1000
                },
                cutout: '0%',
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                let label = context.label || "";

                                if (label) {
                                    label += ": ";
                                }
                                label += context.parsed + " points";
                                return label;
                            },
                        },
                    },
                },
                responsive: true,
                maintainAspectRatio: false
            },
        });
    </script>

    
    <%@ include file="../footer.jsp" %>