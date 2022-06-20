const { jsPDF } = require("jspdf");

const doc = new jsPDF();

doc.text("HELLO", 10, 10)
doc.save("a4.pdf")