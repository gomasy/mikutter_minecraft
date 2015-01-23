require "socket"
require "cgi"

S_PORT, R_PORT = 29653, 29654

Plugin.create(:mikutter_minecraft) do
  filter_gui_postbox_post do |gui_postbox|
    str = Plugin.create(:gtk).widgetof(gui_postbox).widget_post.buffer.text
    if str =~ /^@mc\s(.+)$/
      begin
        sock = TCPSocket.open("localhost", S_PORT)
        sock.puts(CGI.escape($1))
        sock.close
      rescue Errno::ECONNREFUSED => e
        activity(:system, "通信に失敗しました。Minecraft が起動していることを確認してください。 - #{e.message}")
      ensure
        Plugin.call(:before_postbox_post, str)
        Plugin.create(:gtk).widgetof(gui_postbox).widget_post.buffer.text = ""
        Plugin.filter_cancel!
      end
    end
    [gui_postbox]
  end

  th = []
  th << Thread.new do
    srv = TCPServer.open(R_PORT)

    loop do
      sock = srv.accept

      while buf = sock.gets
        activity(:system, CGI.unescape(buf))
      end

      sock.close
    end
  end

  th.join
end
