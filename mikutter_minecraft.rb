require "socket"

PORT = 29653

Plugin.create(:mikutter_minecraft) do
  filter_gui_postbox_post do |gui_postbox|
    Plugin.call(:before_postbox_post, Plugin.create(:gtk).widgetof(gui_postbox).widget_post.buffer.text)
    Plugin.create(:gtk).widgetof(gui_postbox).widget_post.buffer.text = ""
    Plugin.filter_cancel!
  end

  on_before_postbox_post do |mes|
    if mes =~ /^@mc\s(.+)$/
      begin
        s = TCPSocket.open("localhost", PORT)
        s.puts($1)
        s.close
      rescue Errno::ECONNREFUSED => e
        activity(:system, "通信に失敗しました。Minecraft が起動していることを確認してください。 - #{e.message}")
      end
    end
  end
end
