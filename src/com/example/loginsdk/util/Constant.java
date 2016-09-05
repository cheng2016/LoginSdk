package com.example.loginsdk.util;

/**
 * Created by mitnick.cheng on 2016/9/2.
 */

public class Constant {
    public static String QQ_APP_ID = "1105653748";
    public final static String WX_APP_ID = "wx4187133a2fe5bee7";//wx18f2bcc7e49b6dd8
    public final static String WX_APP_SECRET = "8dc7480a98bf249d0d40b98478ba2415";//8445a1613e3123546b007a68d00faf53
    public final static String WX_GRANT_TYPE = "authorization_code";

    public final static String WEIXIN = "-----BEGIN CERTIFICATE-----\n" +
            "MIIHqDCCBpCgAwIBAgIQBqNJx7yh4mP8h5BwE0YyiTANBgkqhkiG9w0BAQsFADBE\n" +
            "MQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMU\n" +
            "R2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTYwNDAxMDAwMDAwWhcNMTgwMTI4MjM1\n" +
            "OTU5WjCBmTELMAkGA1UEBhMCQ04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UE\n" +
            "BxQIU2hlbnpoZW4xOjA4BgNVBAoUMVNoZW56aGVuIFRlbmNlbnQgQ29tcHV0ZXIg\n" +
            "U3lzdGVtcyBDb21wYW55IExpbWl0ZWQxDDAKBgNVBAsUA1ImRDEZMBcGA1UEAxQQ\n" +
            "bXAud2VpeGluLnFxLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
            "ALzUvYlLdW2kf9yuF4k51+Jnn8R1eUdvo3ifE6+1FkGRS+NoZDF1rupuQy52NLQG\n" +
            "2PQjfJ8HsigMVxSscRrbxqgpMOqLIvh+xgq+rIetPWPLL0FuqX8NRspksVuTjs8v\n" +
            "EF1riF/DuuFq0ZCQzE+YBS+i4Cd5rgHCG83FJZ6kUdQQ+yl8k4Pqa2QCDfSxuUUd\n" +
            "92PQJfdDGxQbZLdXBodBecIqSBLBrvV2v5JhMRJ/IYEpEzKeUayt4rZW4YWC0A3F\n" +
            "h88rOrSCZ4+kg+PdO5ql2TmtehoEptCfGAaXYcS/kz/ioywZw3ctF02yUQE8oqjH\n" +
            "6mjyTT1AYpV2mVMsriydfjECAwEAAaOCBD4wggQ6MIIBMwYDVR0RBIIBKjCCASaC\n" +
            "F2xvbmcub3Blbi53ZWl4aW4ucXEuY29tghJvcGVuLndlaXhpbi5xcS5jb22CFWhr\n" +
            "Lm9wZW4ud2VpeGluLnFxLmNvbYITbXAud2VpeGluYnJpZGdlLmNvbYISZ2FtZS53\n" +
            "ZWl4aW4ucXEuY29tghNzei5tcC53ZWl4aW4ucXEuY29tghRzaC5hcGkud2VpeGlu\n" +
            "LnFxLmNvbYIVc3oub3Blbi53ZWl4aW4ucXEuY29tghNoay5tcC53ZWl4aW4ucXEu\n" +
            "Y29tgg9hLndlaXhpbi5xcS5jb22CFHN6LmFwaS53ZWl4aW4ucXEuY29tghFhcGku\n" +
            "d2VpeGluLnFxLmNvbYIUaGsuYXBpLndlaXhpbi5xcS5jb22CEG1wLndlaXhpbi5x\n" +
            "cS5jb20wCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCBaAwKwYDVR0fBCQwIjAgoB6g\n" +
            "HIYaaHR0cDovL2duLnN5bWNiLmNvbS9nbi5jcmwwgZ0GA1UdIASBlTCBkjCBjwYG\n" +
            "Z4EMAQICMIGEMD8GCCsGAQUFBwIBFjNodHRwczovL3d3dy5nZW90cnVzdC5jb20v\n" +
            "cmVzb3VyY2VzL3JlcG9zaXRvcnkvbGVnYWwwQQYIKwYBBQUHAgIwNQwzaHR0cHM6\n" +
            "Ly93d3cuZ2VvdHJ1c3QuY29tL3Jlc291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMB0G\n" +
            "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAfBgNVHSMEGDAWgBTSb/eW9IU/\n" +
            "cjwwfSPahXibo3xafDBXBggrBgEFBQcBAQRLMEkwHwYIKwYBBQUHMAGGE2h0dHA6\n" +
            "Ly9nbi5zeW1jZC5jb20wJgYIKwYBBQUHMAKGGmh0dHA6Ly9nbi5zeW1jYi5jb20v\n" +
            "Z24uY3J0MIIBfgYKKwYBBAHWeQIEAgSCAW4EggFqAWgAdgDd6x0reg1PpiCLga2B\n" +
            "aHB+Lo6dAdVciI09EcTNtuy+zAAAAVPPsRPWAAAEAwBHMEUCIEpHYsY1PSzqxTQR\n" +
            "hXX8Dm7KPHOoupHKPM/C+Zoyz565AiEA1eo45O8i9sNx4hp+lI0PjEQEyUO6yUyH\n" +
            "KFxQymaMGBUAdQCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAVPP\n" +
            "sRQbAAAEAwBGMEQCIBtawmqsd59E+ix03ZJ2f0HPMrWMGS9IManYi9kZfCkHAiA7\n" +
            "uInuX0V3cyqWg8NDwrW7IEQQiipauhq7qFtWnJ7qHQB3AGj2mPgfZIK+OozuuSgd\n" +
            "TPxxUV1nk9RE0QpnrLtPT/vEAAABU8+xFBwAAAQDAEgwRgIhAIItwFOzbWaTirf8\n" +
            "j4cWuDK4gC39ILLu1HeG/bh7lEZwAiEAtoypAS0bx9DeCDdOerDwAUzu4LVjlTF2\n" +
            "goBnTyvtxOYwDQYJKoZIhvcNAQELBQADggEBAAinkTjQgWWj6pGKB2E1f1B/Xpml\n" +
            "cqQFY9sESVXkXn96H4dvSRGG+oeeWkuSIzOnCTQbKusIRLRm0TFMmcje0QTKg7yv\n" +
            "ibvd389P976onOJkiocZQW+PYhQOpm3yCFs9ckmoEddLueLrT9weLteA9k2wpr3h\n" +
            "Tj78d4j+nK+IRfmwm5spxuzbHvl52d8B+nA+laNBUveRSbodx8xpI6qRNy0RtoYq\n" +
            "aGWLT/x+nesbtddUgkosy9NWVMUiPu2yFjQXmNqCbWaef3kaDw0LK9SXrYZUFoDS\n" +
            "w3aEAgULiO7qXPJEWGfVEP+9ZlcA1kRIYw8OLK58x0mt+TKP7PZGwMoSzpk=\n" +
            "-----END CERTIFICATE-----\n";
}
