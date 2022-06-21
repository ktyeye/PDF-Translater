const fs = require('fs')

/**
 * TODO(developer): Uncomment these variables before running the sample.
 */
const projectId = 'healthy-bonsai-353912';
const location = 'global';
const inputUri = 'gs://diffrag_translate/test.pdf';

// Imports the Google Cloud Translation library
const {TranslationServiceClient} = require('@google-cloud/translate').v3beta1;

// Instantiates a client
const translationClient = new TranslationServiceClient();

const documentInputConfig = {
  gcsSource: {
    inputUri: inputUri,
  },
};

async function translateDocument() {
  // Construct request

  // Run request
  const [response] = await translationClient.translateDocument({
    parent: translationClient.locationPath(projectId, location),
    documentInputConfig: documentInputConfig,
    sourceLanguageCode: 'ko-KR',
    targetLanguageCode: 'en-US',
    documentOutputConfig: { gcsDestination: { outputUriPrefix: 'gs://diffrag_translate/output/' } }
  });

  fs.writeFileSync('output.pdf', response.documentTranslation.byteStreamOutputs.toString())

  console.log(
    `Response: Mime Type - ${response.documentTranslation.mimeType}`
  );
}

translateDocument();