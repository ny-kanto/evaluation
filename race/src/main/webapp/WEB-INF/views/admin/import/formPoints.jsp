<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="home.renovation.home.entity.*, java.util.List" %>
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
                                <li class="breadcrumb-item"><a href="#">Import</a></li>
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
                            <h4 class="card-title mb-0 flex-grow-1">Importation points</h4>
                        </div><!-- end card header -->
                        <div class="card-body">
                            <div class="live-preview">
                                <form action="/admin/import-data-points/save" method="post" enctype="multipart/form-data">
                                    <div class="row mb-3">
                                        <div class="col-lg-3">
                                            <label for="nameInput" class="form-label">Points</label>
                                        </div>
                                        <div class="col-lg-9">
                                            <input type="file" class="form-control" id="nameInput"
                                                placeholder="Importer les points" name="points">
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <div class="text-start">
                                            <button type="button" class="btn btn-info bg-gradient"><a href="/devis/list"><< Retour</a></button>
                                        </div>
                                        <div class="text-end">
                                            <button type="submit" class="btn btn-primary bg-gradient">Importer</button>
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