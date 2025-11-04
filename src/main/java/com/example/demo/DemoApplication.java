package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;


import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication{
    private final VectorStore vectorStore;
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;


//    @Override
//    public void run(String... args) throws Exception{
//        List<String> contents = List.of(
//                "Alex Chen is a 28-year-old software engineer currently working at TechInnovate Solutions in Seattle, Washington. He graduated from Stanford University with a Master's degree in Computer Science, specializing in artificial intelligence and distributed systems. Alex has been working in the tech industry for 6 years and has extensive experience with cloud computing, microservices architecture, and machine learning implementations.",
//
//                "Professional Background: Alex started his career as a junior developer at Amazon Web Services, where he worked on scaling distributed systems for enterprise clients. After three years, he moved to TechInnovate Solutions as a Senior Software Engineer, where he leads a team of 8 developers working on next-generation AI-powered applications. His current project involves developing a recommendation engine that processes over 10 million user interactions daily.",
//
//                "Technical Expertise: Alex is proficient in Java, Python, and Go programming languages. He has deep knowledge of Spring Boot framework, Docker containerization, Kubernetes orchestration, and various cloud platforms including AWS and Google Cloud. Recently, he's been exploring quantum computing applications and has published two research papers on quantum algorithms for optimization problems.",
//
//                "Personal Life: Outside of work, Alex is an avid rock climber and has climbed several challenging routes in Yosemite National Park. He volunteers as a coding instructor for underprivileged youth through CodeForGood non-profit organization. Alex is also a passionate photographer, specializing in landscape and urban photography, and has had his work featured in several local art exhibitions.",
//
//                "Education and Certifications: Alex Chen holds a M.S. in Computer Science from Stanford University (2018) and a B.S. in Software Engineering from University of Washington (2016). He has AWS Solutions Architect Professional Certification and Google Cloud Professional Data Engineer Certification.",
//
//                "Recent Projects: Project Athena - Developed a real-time fraud detection system that reduced false positives by 40%. Nexus Platform - Built a microservices architecture serving 5 million monthly active users. AI Mentor - Created an intelligent coding assistant that helps junior developers improve their skills.",
//
//                "Community Involvement: Alex regularly speaks at tech conferences about the ethical implications of AI and frequently mentors new developers through the Seattle Tech Mentorship Program. He also contributes to several open-source projects focused on educational technology and has developed a popular VS Code extension for code quality analysis.",
//
//                "Personal Philosophy: Alex Chen believes that technology should be accessible to everyone and that we have a responsibility to build systems that are not only efficient but also ethical and inclusive. He states: The most exciting developments in tech happen at the intersection of different disciplines and perspectives.",
//
//                "Future Goals: Alex plans to start his own AI ethics consulting firm within the next two years, focusing on helping organizations implement responsible AI practices. He's also working on a book about the future of human-computer interaction in the age of ubiquitous computing.",
//
//                "Fun Facts about Alex: He is fluent in Mandarin and Spanish, has completed three marathons, built his first computer at age 12, collects vintage mechanical keyboards, and has a black belt in Taekwondo.",
//
//                "Contact Information: Alex Chen can be reached at alex.chen@techinnovate.com or through his professional portfolio at alex-chen-dev.com. He's active on LinkedIn and GitHub for professional networking and open-source contributions."
//        );
//        List<Document> documents = IntStream.range(0,contents.size())
//                .mapToObj(i -> Document.builder()
//                        .text(contents.get(i))
//                        .build())
//                .toList();
//        vectorStore.add(documents);
//    }


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .promptTemplate(PromptTemplate.builder().template("""
                            You are a helpful AI assistant. Use the following rules:
                            
                            - If the user asks a factual question that can be answered using the provided context, use the context to give a helpful answer
                            - If the user asks a general question, greeting, or conversation that doesn't require factual context, respond naturally as a helpful AI
                            - If the context doesn't contain relevant information for a factual question, say you don't know
                            
                            Context (for factual questions):
                            {question_answer_context}
                            
                            User Question: {query}
                            
                            Your response:
                            """
                                ).build())
                        .build()
                )
                .build();
    }
}
