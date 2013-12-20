; wireshark-qt
; tcp.port eq 9500

; server connection
(def sc (server/bind "127.0.0.1" 9500))
(server/accept sc)

; client connection
(def cc (client/connect "127.0.0.1" 9500))
(time (client/write cc (core/rand-byte-buffer 100000)))
