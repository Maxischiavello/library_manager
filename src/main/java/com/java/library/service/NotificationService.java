package com.java.library.service;

import com.java.library.dto.NotificationDTO;
import com.java.library.model.Notification;
import com.java.library.model.User;
import com.java.library.repository.NotificationRepository;
import com.java.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<NotificationDTO> getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.map(this::convertToDTO);
    }

    public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
        Notification notification = convertToEntity(notificationDTO);
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setUserId(notification.getUser().getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setSendDate(notification.getSendDate());
        return notificationDTO;
    }

    private Notification convertToEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());

        Optional<User> user = userRepository.findById(notificationDTO.getUserId());
        user.ifPresent(notification::setUser);

        notification.setMessage(notificationDTO.getMessage());
        notification.setSendDate(notificationDTO.getSendDate());

        return notification;
    }
}
