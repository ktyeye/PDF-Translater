
// const  projectId = 'healthy-bonsai-353912'


// const {Translate} = require('@google-cloud/translate').v2;

// // Instantiates a client
// const translate = new Translate({projectId});

// async function quickStart() {
//   // The text to translate
//   const text = 'Hello, world!';

//   // The target language
//   const target = 'ko';

//   // Translates some text into Russian
//   const [translation] = await translate.translate(text, target);
//   console.log(`Text: ${text}`);
//   console.log(`Translation: ${translation}`);
// }

// quickStart();


/*  사용하기 전에 환경변수로 GOOGLE_APPLICATION_CREDENTIALS=/Users/ktyeye/Desktop/PDF_Translater/keycode/healthy-bonsai-353912-f91dcf70b539.json 설정해주어야 한다.
*   해당 파일은 Google Console에서 Project API key를 받아야한다.
*/

// Imports the Google Cloud client library
const {Translate} = require('@google-cloud/translate').v2;

// Creates a client
const translate = new Translate();

/**
 * TODO(developer): Uncomment the following lines before running the sample.
 */
const text = 'The text to translate, e.g. Hello, world!';
const target = 'ko';

async function translateText() {
  // Translates the text into the target language. "text" can be a string for
  // translating a single piece of text, or an array of strings for translating
  // multiple texts.
  let [translations] = await translate.translate(text, target);
  translations = Array.isArray(translations) ? translations : [translations];
  console.log('Translations:');
  translations.forEach((translation, i) => {
    console.log(`${text[i]} => (${target}) ${translation}`);
  });
}

translateText();