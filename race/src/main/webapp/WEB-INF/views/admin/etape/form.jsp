<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% Etape etape = (Etape) request.getAttribute("etape"); %>
<% List<DetailCoureurEtape> detail = (List<DetailCoureurEtape>) request.getAttribute("detail"); %>
<div class="main-content">

    <div class="page-content">
        <div class="container-fluid">
            <!-- start page title -->
            <div class="row">
                <div class="col-12">
                    <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                        <h4 class="mb-sm-0">Form</h4>

                        <div class="page-title-right">
                            <ol class="breadcrumb m-0">
                                <li class="breadcrumb-item"><a href="#">Details</a></li>
                                <li class="breadcrumb-item active">Form</li>
                            </ol>
                        </div>

                    </div>
                </div>
            </div>
            <!-- end page title -->

            <div class="row">
                <div class="col-xxl-10 offset-xxl-1">
                    <div class="card">
                        <div class="card-header align-items-center d-flex">
                            <h4 class="card-title mb-0 flex-grow-1">Affectation des temps aux coureurs a l'<%= etape.getNom() %></h4>
                        </div><!-- end card header -->
                        <div class="card-body">
                            <div class="live-preview">
                                <form action="/admin/etape-coureur/save" method="post">
                                    <input type="hidden" value="<%= etape.getId() %>" name="id_etape">
                                    <div class="mb-4 ms-2">
                                        <% for (DetailCoureurEtape dce : detail) { %>
                                            <!-- Afficher le message d'exception si présent -->
                                            <% String dateError = (String) request.getAttribute("dateError"); %>
                                            <% String etapeError = (String) request.getAttribute("etapeError"); %>
                                            <% if (dateError != null && etapeError != null && etapeError.equals(etape.getId())) { %>
                                                <div class="alert alert-danger" role="alert">
                                                    <%= dateError %>
                                                </div>
                                            <% } %>
                                            <div class="row mb-3 align-items-center">
                                                <div class="col-4">
                                                    <h5><%= dce.getCoureur().getNom() %></h5>
                                                </div>
                                                <div class="col-8">
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="col-3">
                                                                <label for="date-arrive">Date d'arrivée :</label>
                                                            </div>
                                                            <div class="col-9">
                                                                <input type="date" id="date-arrive" class="form-control w-100" name="date_arrive_<%= dce.getCoureur().getId() %>">
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="col-3">
                                                                <label for="heure-arrive">Heure d'arrivée :</label>
                                                            </div>
                                                            <div class="col-9">
                                                                <input type="time" step="1" id="heure-arrive" class="form-control w-100" name="heure_arrive_<%= dce.getCoureur().getId() %>">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        <% } %>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <div class="text-end">
                                            <button type="submit" class="btn btn-primary bg-gradient">Definir</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- container-fluid -->
    </div>
    <%@ include file="../footer.jsp" %>