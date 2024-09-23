package com.hamzadev.synapseai.repository

import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.model.AIAssistant

class AIAssistantRepository {

    private val assistants = listOf(
        // Writing and Content Creation
        AIAssistant(R.drawable.jasperai_logo, "Jasper.ai", "Generates human-quality text for various purposes, including blog posts, social media, and marketing copy."),
        AIAssistant(R.drawable.copyai_logo, "Peppertype", "Creates engaging content for websites, emails, and product descriptions."),
        AIAssistant(R.drawable.peppertype_ai, "Copy.ai", "Generates creative and persuasive content for marketing and advertising."),
        AIAssistant(R.drawable.writesonic_logo, "Writesonic", "Offers a wide range of writing templates for content creation tasks."),
        AIAssistant(R.drawable.anyword_logo, "Anyword", "Optimizes content for increasing conversions or engagement."),

        // Storytelling and Creative Writing
        AIAssistant(R.drawable.sudowrite_logo, "Sudowrite", "Helps writers brainstorm ideas, develop characters, and generate plotlines."),
        AIAssistant(R.drawable.ryter_logo, "Rytr", "Assists with creative writing tasks including generating story ideas."),
        AIAssistant(R.drawable.dungeon_logo, "AIDungeon", "A text-based adventure game to create and explore stories."),
        AIAssistant(R.drawable.novelai_logo, "NovelAI", "A tool for generating stories, poems, and creative text formats."),

        // Education and Learning
        AIAssistant(R.drawable.socratic_logo, "Socratic", "Helps students understand complex concepts by asking questions and providing explanations."),
        AIAssistant(R.drawable.duolingo_logo, "Duolingo", "A language learning app that personalizes the learning experience."),
        AIAssistant(R.drawable.quizlet_logo, "Quizlet", "Uses AI to personalize study sessions and track progress with flashcards."),
        AIAssistant(R.drawable.coursera_logo, "Coursera", "Online courses with AI-powered tools to support learners."),

        // Research and Analysis
        AIAssistant(R.drawable.chatgpt_logo, "ChatGPT", "Summarizes research papers, analyzes data, and generates insights."),
        AIAssistant(R.drawable.wolfram_logo, "Wolfram Alpha", "A computational engine providing factual answers to a wide range of questions."),
        AIAssistant(R.drawable.ottar_logo, "Otter.ai", "A transcription app that automatically transcribes audio and organizes notes."),
        AIAssistant(R.drawable.sematic_logo, "Semantic Scholar", "Analyzes academic papers and discovers relevant research."),
        AIAssistant(R.drawable.google_scholar_logo, "Google Scholar", "A search engine for scholarly literature that uses AI to filter results."),

        // Coding and Programming
        AIAssistant(R.drawable.github_copilot_logo, "GitHub Copilot", "An AI-powered code completion tool suggesting code snippets."),
        AIAssistant(R.drawable.tabnine_logo, "Tabnine", "Helps programmers write code faster with AI-powered code completion."),
        AIAssistant(R.drawable.replit_logo, "Replit", "Cloud-based IDE with AI-powered code suggestions and error detection."),
        AIAssistant(R.drawable.codewhisperer_logo, "CodeWhisperer", "AI-powered code generation tool for fast and accurate development."),
        AIAssistant(R.drawable.kite_ai_logo, "Kite", "A code completion tool using AI to suggest and complete code snippets."),

        // Design and Creativity
        AIAssistant(R.drawable.midjourney_logo, "Midjourney", "AI art generator creating stunning images based on text prompts."),
        AIAssistant(R.drawable.dalle_logo, "DALL-E 2", "AI art generator producing realistic and imaginative images from text."),
        AIAssistant(R.drawable.canva_logo, "Canva", "Graphic design platform with AI-suggested design elements."),
        AIAssistant(R.drawable.stabe_diffusion_logo, "Stable Diffusion", "Generates high-quality images from text descriptions."),
        AIAssistant(R.drawable.adobe_logo, "Adobe Firefly", "Generative AI tool for creating images, text, and audio content."),

        // Business and Productivity
        AIAssistant(R.drawable.hubspot_logo, "HubSpot Sales Hub", "Automates tasks, improves lead generation, and personalizes interactions."),
        AIAssistant(R.drawable.salesforce_logo, "Salesforce Einstein", "Predicts customer behavior and optimizes marketing campaigns with AI."),
        AIAssistant(R.drawable.todoist_logo, "Todoist", "Task management app using AI to prioritize tasks and suggest deadlines."),
        AIAssistant(R.drawable.asana_logo, "Asana", "Project management tool that uses AI to automate workflows and track progress."),
        AIAssistant(R.drawable.trello_logo, "Trello", "Collaboration tool that uses AI to suggest cards and labels for organization."),

        // Language Translation and Interpretation
        AIAssistant(R.drawable.google_translate_logo, "Google Translate", "Language translation tool that translates text, speech, and images."),
        AIAssistant(R.drawable.deepl_logo, "DeepL", "High-quality language translation service."),
        AIAssistant(R.drawable.yandex_logo, "Yandex Translate", "Russian-based translation service supporting many languages."),
        AIAssistant(R.drawable.microsoft_translator_logo, "Microsoft Translator", "Translation service integrated with Microsoft products."),

        // Healthcare and Medicine
        AIAssistant(R.drawable.babylon, "Babylon Health", "Healthcare platform for remote consultations, diagnoses, and prescriptions."),
        AIAssistant(R.drawable.ada_logo, "Ada Health", "Health assistant app helping users assess symptoms and seek advice."),
        AIAssistant(R.drawable.waston_logo, "Watson Health", "AI-powered tools for analyzing medical data and improving patient outcomes."),
        AIAssistant(R.drawable.healthtap_logo, "Healthtap", "Telehealth platform connecting patients with doctors for consultations."),

        // Personal Assistance and Companionship
        AIAssistant(R.drawable.replika_logo, "Replika", "A chatbot designed to provide emotional support and companionship."),
        AIAssistant(R.drawable.alexa_logo, "Amazon Alexa", "Virtual assistant that performs various tasks from setting alarms to playing music."),
        AIAssistant(R.drawable.google_assistant_logo, "Google Assistant", "Virtual assistant offering a wide range of features and capabilities."),
        AIAssistant(R.drawable.siri_logo, "Siri", "Apple’s virtual assistant for controlling devices and searching for information."),
        AIAssistant(R.drawable.bixby_logo, "Bixby", "Samsung's virtual assistant for controlling devices and performing tasks.")
    )

    fun getAssistantsForCategory(category: String): List<AIAssistant> {
        return when (category) {
            "Writing and Content Creation" -> assistants.filter { it.title in listOf("Jasper.ai", "Peppertype", "Copy.ai", "Writesonic", "Anyword") }
            "Storytelling and Creative Writing" -> assistants.filter { it.title in listOf("Sudowrite", "Rytr", "AIDungeon", "NovelAI") }
            "Education and Research" -> assistants.filter { it.title in listOf("Socratic", "Duolingo", "Khan Academy", "Quizlet", "Coursera") }
            "Research and Analysis" -> assistants.filter { it.title in listOf("ChatGPT", "Wolfram Alpha", "Otter.ai", "Semantic Scholar", "Google Scholar") }
            "Programming Assistants" -> assistants.filter { it.title in listOf("GitHub Copilot", "Tabnine", "Replit", "CodeWhisperer", "Kite") }
            "Creative Arts and Design" -> assistants.filter { it.title in listOf("Midjourney", "DALL-E 2", "Canva", "Stable Diffusion", "Adobe Firefly") }
            "Business and Marketing" -> assistants.filter { it.title in listOf("HubSpot Sales Hub", "Salesforce Einstein", "Todoist", "Asana", "Trello") }
            "Language Translation" -> assistants.filter { it.title in listOf("Google Translate", "DeepL", "Babylon Translator", "Yandex Translate", "Microsoft Translator") }
            "Healthcare and Medicine" -> assistants.filter { it.title in listOf("Babylon Health", "Ada Health", "Watson Health", "Buoy Health", "Healthtap") }
            "Personal Assistants" -> assistants.filter { it.title in listOf("Replika", "Amazon Alexa", "Google Assistant", "Siri", "Bixby") }
            else -> emptyList()
        }
    }
    // New method to get all assistants
    fun getAllAssistants(): List<AIAssistant> {
        return assistants
    }
}
