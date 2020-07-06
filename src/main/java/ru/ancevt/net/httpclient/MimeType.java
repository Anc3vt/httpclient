package ru.ancevt.net.httpclient;

/**
 *
 * @author ancevt
 */
public class MimeType {

    public static final String APPLICATION_ATOM_XML  = "application/atom+xml";
    public static final String APPLICATION_EDI_X12  = "application/EDI-X12";
    public static final String APPLICATION_EDIFACT  = "application/EDIFACT";
    public static final String APPLICATION_JSON  = "application/json";
    public static final String APPLICATION_JAVASCRIPT  = "application/javascript";
    public static final String APPLICATION_OCTET_STREAM  = "application/octet-stream";
    public static final String APPLICATION_OGG  = "application/ogg";
    public static final String APPLICATION_PDF  = "application/pdf";
    public static final String APPLICATION_POSTSCRIPT  = "application/postscript";
    public static final String APPLICATION_SOAP_XML  = "application/soap+xml";
    public static final String APPLICATION_FONT_WOFF  = "application/font-woff";
    public static final String APPLICATION_XHTML_XML  = "application/xhtml+xml";
    public static final String APPLICATION_XML_DTD  = "application/xml-dtd";
    public static final String APPLICATION_XOP_XML  = "application/xop+xml";
    public static final String APPLICATION_ZIP  = "application/zip";
    public static final String APPLICATION_GZIP  = "application/gzip";
    public static final String APPLICATION_X_BITTORENT  = "application/x-bittorrent";
    public static final String APPLICATION_X_TEX  = "application/x-tex";
    public static final String APPLICATION_XML  = "application/xml";

    public static final String AUDIO_BASIC  = "audio/basic";
    public static final String AUDIO_L24  = "audio/L24";
    public static final String AUDIO_MP4  = "audio/mp4";
    public static final String AUDIO_AAC  = "audio/aac";
    public static final String AUDIO_MPEG  = "audio/mpeg";
    public static final String AUDIO_OGG  = "audio/ogg";
    public static final String AUDIO_VORBIS  = "audio/vorbis";
    public static final String AUDIO_X_MS_WMA  = "audio/x-ms-wma";
    public static final String AUDIO_X_MS_WAX  = "audio/x-ms-wax";
    public static final String AUDIO_VND_RN_REALAUDIO  = "audio/vnd.rn-realaudio";
    public static final String AUDIO_VND_WAVE  = "audio/vnd.wave";
    public static final String AUDIO_WEBM  = "audio/webm";

    public static final String IMAGE_GIF  = "image/gif";
    public static final String IMAGE_JPEG  = "image/jpeg";
    public static final String IMAGE_PJPEG  = "image/pjpeg";
    public static final String IMAGE_PNG  = "image/png";
    public static final String IMAGE_SVG_XML  = "image/svg+xml";
    public static final String IMAGE_TIFF  = "image/tiff";
    public static final String IMAGE_VND_MICROSOFT_ICON  = "image/vnd.microsoft.icon";
    public static final String IMAGE_VND_WAP_WBMP  = "image/vnd.wap.wbmp";
    public static final String IMAGE_WEBP  = "image/webp";

    public static final String MESSAGE_HTTP  = "message/http";
    public static final String MESSAGE_IMDN_XML  = "message/imdn+xml";
    public static final String MESSAGE_PARTIAL  = "message/partial";
    public static final String MESSAGE_RFC822  = "message/rfc822";

    public static final String MODEL_EXAMPLE  = "model/example"; //(RFC 4735)
    public static final String MODEL_IGES  = "model/iges"; //IGS файлы, IGES файлы (RFC 2077)
    public static final String MODEL_MESH  = "model/mesh"; //MSH файлы, MESH файлы (RFC 2077), SILO файлы
    public static final String MODEL_VRML  = "model/vrml"; //WRL файлы, VRML файлы (RFC 2077)
    public static final String MODEL_X3D_BINARY  = "model/x3d+binary"; //X3D ISO стандарт для 3D компьютерной графики, X3DB файлы
    public static final String MODEL_X3D_VRML  = "model/x3d+vrml"; //X3D ISO стандарт для 3D компьютерной графики, X3DV VRML файлы
    public static final String MODEL_X3D_XML  = "model/x3d+xml"; //X3D ISO стандарт для 3D компютерной графики, X3D XML файлы

    public static final String MULTIPART_MIXED  = "multipart/mixed"; //MIME E-mail (RFC 2045 и RFC 2046)
    public static final String MULTIPART_ALTERNATIVE  = "multipart/alternative"; //MIME E-mail (RFC 2045 и RFC 2046)
    public static final String MULTIPART_RELATED  = "multipart/related"; //MIME E-mail (RFC 2387 и используемое MHTML (HTML mail))
    public static final String MULTIPART_FORM_DATA  = "multipart/form-data"; //MIME Webform (RFC 2388)
    public static final String MULTIPART_SIGNED  = "multipart/signed"; //(RFC 1847)
    public static final String MULTIPART_ENCRYPTED  = "multipart/encrypted"; //(RFC 1847)

    public static final String TEXT_CMD  = "text/cmd"; //команды
    public static final String TEXT_CSS  = "text/css"; //Cascading Style Sheets (RFC 2318)
    public static final String TEXT_CSV  = "text/csv"; //CSV (RFC 4180)
    public static final String TEXT_HTML  = "text/html"; //HTML (RFC 2854)
    public static final String TEXT_JAVASCRIPT  = "text/javascript (Obsolete)"; //JavaScript (RFC 4329)
    public static final String TEXT_PLAIN  = "text/plain"; //текстовые данные (RFC 2046 и RFC 3676)
    public static final String TEXT_PHP  = "text/php"; //Скрипт языка PHP
    public static final String TEXT_XML  = "text/xml"; //Extensible Markup Language (RFC 3023)
    public static final String TEXT_MARKDOWN  = "text/markdown"; //файл языка разметки Markdown (RFC 7763)

    public static final String VIDEO_MPEG  = "video/mpeg"; //MPEG-1 (RFC 2045 и RFC 2046)
    public static final String VIDEO_MP4  = "video/mp4"; //MP4 (RFC 4337)
    public static final String VIDEO_OGG  = "video/ogg"; //Ogg Theora или другое видео (RFC 5334)
    public static final String VIDEO_QUICKTIME  = "video/quicktime"; //QuickTime[12]
    public static final String VIDEO_WEBM  = "video/webm"; //WebM
    public static final String VIDEO_X_MS_WMV  = "video/x-ms-wmv"; //Windows Media Video[6]
    public static final String VIDEO_X_FLV  = "video/x-flv"; //FLV
    public static final String VIDEO_3GPP  = "video/3gpp"; //.3gpp .3gp [13]
    public static final String VIDEO_3GPP2  = "video/3gpp2"; //.3gpp2 .3g2 [13]

    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT  = "application/vnd.oasis.opendocument.text"; //OpenDocument[14]
    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET  = "application/vnd.oasis.opendocument.spreadsheet"; //OpenDocument[15]
    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION  = "application/vnd.oasis.opendocument.presentation"; //OpenDocument[16]
    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS  = "application/vnd.oasis.opendocument.graphics"; //OpenDocument[17]
    public static final String APPLICATION_VND_MS_EXCEL  = "application/vnd.ms-excel"; //Microsoft Excel файлы
    public static final String APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET  = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; //Microsoft Excel 2007 файлы
    public static final String APPLICATION_VND_MS_POWERPOINT  = "application/vnd.ms-powerpoint"; //Microsoft Powerpoint файлы
    public static final String APPLICATION_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION  = "application/vnd.openxmlformats-officedocument.presentationml.presentation"; //Microsoft Powerpoint 2007 файлы
    public static final String APPLICATION_MSWORD  = "application/msword"; //Microsoft Word файлы
    public static final String APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT  = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"; //Microsoft Word 2007 файлы
    public static final String APPLICATION_VND_MOZILLA_XUL_XML  = "application/vnd.mozilla.xul+xml"; //Mozilla XUL файлы
    public static final String APPLICATION_VND_GOOGLE_EARTH_KML_XML  = "application/vnd.google-earth.kml+xml"; //KML файлы (например, для Google Earth)
    public static final String APPLICATION_X_WWW_FORM_URLENCODED  = "application/x-www-form-urlencoded";// Form Encoded Data[18]
    public static final String APPLICATION_X_DVI  = "application/x-dvi"; //DVI
    public static final String APPLICATION_X_LATEX  = "application/x-latex"; //LaTeX файлы
    public static final String APPLICATION_X_FONT_TTF  = "application/x-font-ttf"; //TrueType (не зарегистрированный MIME-тип, но наиболее часто используемый)
    public static final String APPLICATION_X_SHOCKWAVE_FLASH  = "application/x-shockwave-flash"; //Adobe Flash[19] и[20]
    public static final String APPLICATION_X_STUFFIT  = "application/x-stuffit"; //StuffIt
    public static final String APPLICATION_X_RAR_COMPRESSED  = "application/x-rar-compressed"; //RAR
    public static final String APPLICATION_X_TAR  = "application/x-tar"; //Tarball

    public static final String TEXT_X_JQUERY_TMPL  = "text/x-jquery-tmpl"; //jQuery
    public static final String APPLICATION_X_JAVASCRIPT  = "application/x-javascript"; //
    public static final String APPLICATION_X_PKCS12  = "application/x-pkcs12"; //pfx файлы
    public static final String APPLICATION_X_PKCS7_CERTIFICATES  = "application/x-pkcs7-certificates"; //p7b файлы
    public static final String APPLICATION_X_PKCS7_CERTREQRESP  = "application/x-pkcs7-certreqresp"; //p7r файлы
    public static final String APPLICATION_X_PKCS7_MIME  = "application/x-pkcs7-mime"; //p7m файлы
    public static final String APPLICATION_X_PKCS7_SIGNATURE  = "application/x-pkcs7-signature"; //p7s файлы
}
