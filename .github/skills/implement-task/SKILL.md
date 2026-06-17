---
name: implement-task
description: >
  Implement a task from any provider (JIRA, GitHub, etc.) with planning, agent assignment, and git workflow. Use PROACTIVELY to execute the implementation steps, but do NOT commit changes.
---

**Purpose**: Implement a task from any provider (JIRA, GitHub, etc.) with planning, agent assignment, and git workflow.

**Input**: Task identifier and optional provider hint (e.g., `PROJ-123`, `github.com/org/repo/issues/45`, or URL)

---

## Workflow

### 1. Task Validation & Extraction
- Parse task identifier to determine provider (JIRA, GitHub, GitLab, etc.)
- Fetch task details (title, description, acceptance criteria) using MCP server or provider CLI
- Fail gracefully if task not found or provider unknown

### 2. Agent Selection
Find available custom agents and select the most appropriate one for the task implementation. If ambiguous → ask user to select.

### 3. Git Setup

```bash
git fetch origin
git checkout main && git pull origin main
```
- Ensure main or master branch is up-to-date before branching
- Create new branch to implement the task

### 4. Implementation Phase

**Agent prompt structure**:
1. **Analyze**: Review task requirements and current codebase. If task is vague, ask user for clarification. ALWAYS ensure full understanding of what has to be done.
2. **Plan**: Propose implementation approach, affected files, design decisions
3. **Await approval**: Present plan to user, pause until `proceed` signal
4. **Implement**: Execute changes with context from planning phase
5. **Summary**: List files changed, features added/modified, testing guidance

**Agent instructions**:
- Do NOT commit changes
- Flag any breaking changes or risks
- Provide testing recommendations

### 5. Return Summary
- Branch name and status
- Files changed (with line counts)
- New dependencies or config changes
- Testing steps required
- Risk/notes for PR review

---

## Error Handling
- Unknown task ID → Ask for task URL or identifier format
- Ambiguous agent match → Show matching agents, ask user to select
- Git failures → Skip branching, warn user, allow manual setup
- Incomplete task details → Ask user for missing context

## Assumptions
- User has local repo cloned and git configured
- User has access to all task providers via MCP or provider CLI (JIRA MCP Server, GitHub CLI, glab, etc.)
- Main branch reflects the default/target branch for the repo
