# DocPortal Project - Automated Document Processing and Secure Document Uploads

## Problem Identified
The traditional method of scanning and uploading documents on web portals is insecure and inconvenient. Uploading sensitive documents via a third-party device poses security risks, and the need for manual scanning, resizing, and enhancement of the documents adds to the inconvenience.

## Market Research
The customers are organizations that require end-users to upload their documents on web portals. The end-users are individuals who want to upload their documents to these organizations securely.

To our knowledge, there is no existing solution in the market that can fully automate and secure the process of uploading documents to online portals. Our proposed solution aims to address the security risks and streamline the application process for all users.

## Project Description
The DocPortal project aims to provide a secure and automated platform for uploading documents on web portals. The solution eliminates the need for third-party operators for document uploads, reducing security risks and increasing privacy for users.

The solution achieves this by providing an API service to web portals through which documents can be securely transferred without the intervention of local operators. The process involves capturing an image of the document using the mobile device's camera, transferring the image to a cloud-based storage server for further processing using machine learning algorithms for pre-processing, and securely transferring the document to the web portal using encryption techniques such as SSL/TLS.

The core feature of the app is its ability to scan documents and send them directly to a website or service that requires them, without the need for third-party operators. The app's built-in API pre-processes the scanned documents using openCV libraries, adjusting size, contrast, brightness, and other parameters to ensure optimal quality. 

The API provides a simple and secure way for web portals to access documents without requiring physical or manual intervention. The machine learning algorithms enable automatic image processing, reducing the need for manual adjustments and improving document quality. The cloud-based storage solution allows for scalable and cost-effective storage of documents. The use of encryption techniques ensures the confidentiality and integrity of document transfers, protecting sensitive information from unauthorized access or modification.

## Technology Stack
**The project uses the following technologies:**
- Android App Development: XML, JAVA
- API: RAML, Python, Flask/Django
- Database: MongoDB Atlas
- Cloud: Storage server & Database server
## Innovativeness
The DocPortal project is innovative as it utilizes advanced technologies such as machine learning and blockchain to improve the traditional document sharing process. The use of image processing techniques such as de-skewing, thresholding, and image normalization ensures that scanned documents are accurately captured, resulting in better readability and making it easier to identify text and other critical elements.

The use of blockchain technology ensures that the documents are securely stored, providing tamper-proof records of document ownership and access. This provides an extra layer of security to protect sensitive information from unauthorized access or modification.
## Acknowledgements
We would like to express our gratitude to all those who have contributed to the success of this project. First and foremost, we would like to thank our mentor who provided us with valuable guidance and support throughout the development process.

Lastly, we would like to acknowledge the support and encouragement provided by our friends and family throughout this project. Their constant motivation and belief in our abilities have been instrumental in our success.


