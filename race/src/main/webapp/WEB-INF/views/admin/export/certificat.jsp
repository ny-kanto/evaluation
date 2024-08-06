<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="race.team.race.entity.*, java.util.List" %>
<%@ include file="../header.jsp" %>
<% Equipe equipe = (Equipe) request.getAttribute("equipe"); %>
<%-- <% String points = (String) request.getAttribute("points"); %> --%>
<style type='text/css'>
            body {
                font-family: Roboto, sans-serif;
            }

            .certificate-container {
                padding: 20px;
                width: 1024px;
            }

            .certificate {
                border: 20px solid #0C5280;
                padding: 25px;
                height: auto;
                position: relative;
                overflow: hidden;
            }

            .certificate-header>.logo {
                width: 80px;
                height: 80px;
            }

            .certificate-title {
                text-align: center;
            }

            .certificate-body {
                text-align: center;
            }

            h1 {
                font-weight: 400;
                font-size: 36px;
                color: #0C5280;
            }

            .student-name {
                font-size: 24px;
            }

            .certificate-content {
                margin: 0 auto;
                width: 750px;
            }

            .about-certificate {
                width: 380px;
                margin: 0 auto;
            }

            .topic-description {
                text-align: center;
            }

            .certificate-footer .row {
                margin-top: 20px;
            }
        </style>
<div class="main-content">
    <div class="page-content">
        <div class="container-fluid">
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
            <div class="row">
                <div class="row g-4 mb-3">
                    <div class="col-sm-auto">
                        <div>
                            <button type="button" class="btn btn-success add-btn bg-gradient" onClick="printPdf()">Export PDF</button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="certificate-container">
                            <div class="certificate modal-body-pdf">
                                <div class="water-mark-overlay"></div>
                                <div class="certificate-body">
                                    <p class="certificate-title"><strong>RUNNING CHAMPION</strong></p>
                                    <h1>Certificate of Completion</h1>
                                    <p class="student-name">TEAM : <%= equipe.getNom() %></p>
                                    <%-- <p class="student-name">POINTS : <%= points %></p> --%>
                                    <div class="certificate-content">
                                        <div class="about-certificate">
                                            <p>has successfully accomplished the Track & Field match from Team Company</p>
                                        </div>
                                        <div class="text-center">
                                            <p class="topic-description text-muted">We truly appreciate the hard work, persistence and teamwork, which leads to ultimate victory.</p>
                                            <p>We hope the performance/position remains unbeatable for centuries.</p>
                                        </div>
                                    </div>
                                    <div class="certificate-footer text-muted">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p>Company dirigeant: ______________________</p>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <p>Accredited by</p>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <p>Endorsed by</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/assets/js/export/jspdf.js"></script>
<script src="/assets/js/export/html2pdf.js"></script>
<script src="/assets/js/export/xlsx.full.min.js"></script>
<script src="/assets/js/export/FileSaver.min.js"></script>
<script src="/assets/js/main.js"></script>
<%@ include file="../footer.jsp" %>
