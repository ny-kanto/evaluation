<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% Etape etape = (Etape) request.getAttribute("etape"); %>
<% List<Coureur> coureur = (List<Coureur>) request.getAttribute("coureur"); %>
<% List<Equipe> equipe = (List<Equipe>) request.getAttribute("equipe"); %>
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
                            <h4 class="card-title mb-0 flex-grow-1">Affectation des coureurs a l'etape</h4>
                        </div><!-- end card header -->
                        <div class="card-body">
                            <div class="live-preview">
                                <form action="/user/etape-coureur/save" method="post">
                                    <input type="hidden" value="<%= etape.getId() %>" name="id_etape">
                                    <% for (Equipe e : equipe) { %>
                                    <div class="mb-3">
                                        <label for="<%= e.getId() %>" class="form-label"><%= e.getNom() %></label>
                                        <% for (int i = 0; i < etape.getNbCoureurEquipe(); i++) { %>
                                        <select id="<%= e.getId() %>" class="form-select mb-2" name="equipe<%= e.getId() %>coureur<%= i %>">
                                            <option selected>Choisir...</option>
                                            <% for (Coureur c : coureur) {
                                                if (c.getEquipe().getId() == e.getId()) { %>
                                                    <option value="<%= c.getId() %>"><%= c.getNom() %></option>
                                                <% } %>
                                            <% } %>
                                        </select>
                                        <% } %>
                                    </div>
                                    <% } %>
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