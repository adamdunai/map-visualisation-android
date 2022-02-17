# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# todoist
todoist.warn_for_todos
todoist.print_todos_table

# ktlint
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report "/bitrise/src/app/build/reports/ktlint.xml"
