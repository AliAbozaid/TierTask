
#create git tag and push it to gitlab
lane :tag do |options|
	tag = "#{ENV["FLAVOR"]}/#{options[:version_name]}/#{options[:version_code]}"
	add_git_tag(tag: tag)
	push_git_tags(tag: tag)
end


#send message to slack (success)
lane :send_slack do |options|
  release_notes = "#{options[:release_notes]}\n_Branch: #{options[:branch]}_"
  attachments = {fields: [{title: "Release Notes",value: release_notes, short: false}]} if release_notes.to_s.length != 0
  slack(
      message: " `#{ENV["FLAVOR"].upcase} #{options[:beta_type]} #{options[:build_type]} #{options[:version_name]} (#{options[:version_code]}) #{options[:msg]}`",
      slack_url: ENV["SLACK_URL"],
      default_payloads:[],
      channel: "#pilulka-go",
      attachment_properties: attachments ,
      icon_url: options[:slack_icon],
      username: "#{ENV["FLAVOR"].capitalize} Pharm",
      success: options[:is_success]
  )
end

#send message to slack (failure)
lane :send_slack_fail do |lane, exception, options|
  error_message = "Lane: _#{lane}_\n_Exception: #{exception}_"
  attachments = {fields: [{title: "Error Details", value: error_message, short: false}]} if error_message.to_s.length != 0
  slack(
      message: " `#{ENV["FLAVOR"].upcase} #{options[:beta_type]} #{options[:build_type]} #{options[:version_name]} (#{options[:version_code]}) #{options[:msg]}`",
      slack_url: ENV["SLACK_URL"],
      default_payloads:[],
      channel: "#pilulka-go",
      attachment_properties: attachments ,
      icon_url: options[:slack_icon],
      username: "#{ENV["FLAVOR"].capitalize} Pharm",
      success: options[:is_success]
  )
end


def cleanRepo()
  puts `git clean -xdf --exclude="*gradle*" --exclude="local.properties" && git checkout .`
  puts `git status`
end