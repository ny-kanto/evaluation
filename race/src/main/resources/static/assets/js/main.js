function printPdf() {
    var element = document.querySelector('.modal-body-pdf');

    var opt = {
        filename:       'certificat.pdf',
        html2canvas:    { scale: 2 },
        jsPDF:          { unit: 'in', format: 'letter', orientation: 'landscape' }
    };

    html2pdf().set(opt).from(element).save();
}

function exportToExcel() {
    const table = document.getElementById('myTable');
    const rows = table.querySelectorAll('tr');
    const data = [];

    // Parcourir chaque ligne du tableau
    rows.forEach((row) => {
        const rowData = [];
        const cols = row.querySelectorAll('td, th');
        // Parcourir chaque colonne de la ligne
        cols.forEach((col) => {
        rowData.push(col.innerText);
        });
        data.push(rowData);
    });

    // Créer un workbook et une feuille de calcul
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.aoa_to_sheet(data);

    // Ajouter la feuille de calcul au workbook
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    // Convertir le workbook en fichier Excel et le télécharger
    XLSX.writeFile(wb, 'myfile.xlsx');
}

function exportToCSV() {
    const table = document.getElementById('myTable');
    const rows = table.querySelectorAll('tr');
    let csv = '';

    // Parcourir chaque ligne du tableau
    rows.forEach((row) => {
    const rowData = [];
    const cols = row.querySelectorAll('td, th');
    // Parcourir chaque colonne de la ligne
    cols.forEach((col) => {
        rowData.push(col.innerText);
    });
      csv += rowData.join(',') + '\n'; // Ajouter une nouvelle ligne
    });

    // Créer un blob contenant le CSV
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8' });
    // Télécharger le fichier CSV
    saveAs(blob, 'myfile.csv');
}