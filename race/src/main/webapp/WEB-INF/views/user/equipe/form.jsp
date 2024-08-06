<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<% List<Coureur> coureur = (List<Coureur>) request.getAttribute("coureur"); %>
<% Etape etape = (Etape) request.getAttribute("etape"); %>
<% String exceptionMessageNombre = (String) request.getAttribute("exceptionMessageNombre"); %>
<%@ include file="../header.jsp" %>
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
                                <li class="breadcrumb-item"><a href="#">Ajout</a></li>
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
                            <h4 class="card-title mb-0 flex-grow-1">Ajout coureur pour l'etape <%= etape.getNom() %></h4>
                        </div><!-- end card header -->
                        <div class="card-body">
                            <div class="live-preview">
                                <!-- Afficher le message d'exception si présent -->
                                <% if (exceptionMessageNombre != null) { %>
                                    <div class="alert alert-danger" role="alert">
                                        <%= exceptionMessageNombre %>
                                    </div>
                                <% } %>
                                <form action="/user/equipe-etape/save" method="post">
                                    <div class="row mb-3">
                                        <input type="hidden" name="etape" id="etape" value="<%= etape.getId() %>">
                                        <% for (Coureur c : coureur) { %>
                                            <div class="col-lg-3">
                                                <input type="checkbox" class="form-checkbox" name="coureur" id="coureur" value="<%= c.getId() %>">
                                            </div>
                                            <div class="col-lg-9">
                                                <label for="coureur" class="form-label"><%= c.getNom() %></label>
                                                <%-- <select class="form-select w-auto me-1" aria-label=".form-select-sm example" name="coureur">
                                                    <option value="">selectionnez un coureur</option>
                                                    <% for (Coureur c : coureur) { %>
                                                        <option value="<%= c.getId() %>"><%= c.getNom() %></option>
                                                    <% } %>
                                                </select> --%>
                                            </div>
                                        <% } %>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <div class="text-start">
                                            <button type="button" class="btn btn-info bg-gradient"><a href="/user/equipe-etape/list"><< Retour</a></button>
                                        </div>
                                        <div class="text-end">
                                            <button type="submit" class="btn btn-primary bg-gradient">Ajouter</button>
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