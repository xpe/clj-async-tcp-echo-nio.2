; wireshark filter:
; tcp.port eq 9500

; server
(def s (server/bind "127.0.0.1" 9500))
(server/accept s)

; client
(def c (client/connect "127.0.0.1" 9500))
(time (client/write c (core/rand-byte-buffer 10000)))
