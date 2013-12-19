; server connection
(def sc (server/bind "127.0.0.1" 9500))
(server/accept sc)

; client connection
(def cc (client/connect "127.0.0.1" 9500))
(client/send cc (core/byte-buffer [13 12 19]))
