// Fill in  your WiFi networks SSID and password
#define SECRET_SSID "kss"
#define SECRET_PASS "kss3479412"

// Fill in the hostname of your AWS IoT broker
#define SECRET_BROKER "a2uh7vp1chf9ck-ats.iot.ap-northeast-2.amazonaws.com"

// Fill in the boards public certificate
const char SECRET_CERTIFICATE[] = R"(
-----BEGIN CERTIFICATE-----
MIICiTCCAXGgAwIBAgIVAJD+ifGHM4uW3w5C6YSmEcD77u6tMA0GCSqGSIb3DQEB
CwUAME0xSzBJBgNVBAsMQkFtYXpvbiBXZWIgU2VydmljZXMgTz1BbWF6b24uY29t
IEluYy4gTD1TZWF0dGxlIFNUPVdhc2hpbmd0b24gQz1VUzAeFw0yMTEwMjMxMjU3
NTVaFw00OTEyMzEyMzU5NTlaMBgxFjAUBgNVBAMTDU15TUtSV2lGaTEwMTAwWTAT
BgcqhkjOPQIBBggqhkjOPQMBBwNCAAQ0W3d8awMtEiNjaCNKb4FVs+gW4Y+c+ZkX
LKPDKfS4EnVN0vO9YKBhQOSmFaBRTuUnrpHXFKiNjJekQxzypHdro2AwXjAfBgNV
HSMEGDAWgBQ7BIXX5Z+w24LDe+Te0UX1BV407jAdBgNVHQ4EFgQUJULwZ18vbNw+
D/LFA55mzNaKeqswDAYDVR0TAQH/BAIwADAOBgNVHQ8BAf8EBAMCB4AwDQYJKoZI
hvcNAQELBQADggEBAJWhde9yt9IXGIVdivvLas5dU87e3ydvPNXxTrt0HCoFws+U
iooSeudAyByvzSrRXvXnxf7gg5PuaaTWLfWxuig5sbH0WhDIH/8zjxjPdU6xYhVe
1dzexKSmbXKJTaV4l1F/+csRSmICznlYu7zk0FqAFC3GSicff8VLyFWqZlXhDVdZ
v6KkBR6lTGOR3UAWMsRPkKNiEJG67UObOtDpzF210wloyZXDooZeh6J+um2y53iX
WC2uTUrKRRzyv1/9EsZ+BAsmOh7Y4lDoF+fV++upXO8ivq9XzxuVfR23Le800B1a
KBppS0twGfl2bCGhxNt/pibFABy1EvS1MaH/S4A=
-----END CERTIFICATE-----
)";
