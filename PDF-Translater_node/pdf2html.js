const pdf2html = require('pdf2html')
const fs = require('fs')
const stream = fs.createWriteStream('parsed_PDF/text1.html')

pdf2html.html('sample_PDF/test1.pdf', (err, html) => {
	if (err) {
		console.error('Conversion Error: ' + err) 
	} else {
		stream.write(html)
		
	}
})