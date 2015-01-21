require "socket"

PORT = 29653

Plugin.create(:mikutter_minecraft) do
  filter_gui_postbox_post do |gui_postbox|
    str = Plugin.create(:gtk).widgetof(gui_postbox).widget_post.buffer.text
    if str =~ /^@mc\s(.+)$/
      begin
        s = TCPSocket.open("localhost", PORT)
        s.puts($1)
        s.close
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
end
